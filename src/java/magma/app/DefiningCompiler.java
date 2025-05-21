package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.fold.DelimitedFolder;
import magma.app.compile.DivideState;
import magma.app.compile.define.Definition;
import magma.app.compile.define.Parameter;
import magma.app.compile.text.Whitespace;
import magma.app.compile.type.Type;
import magma.app.compile.value.Placeholder;
import magma.app.compile.divide.FoldedDivider;
import magma.app.compile.fold.DecoratedFolder;
import magma.app.compile.fold.TypeSeparatorFolder;
import magma.app.compile.fold.ValueFolder;
import magma.app.compile.locate.FirstLocator;
import magma.app.compile.select.LastSelector;
import magma.app.compile.split.LocatingSplitter;

final class DefiningCompiler {
    public static Iterable<Definition> retainDefinitionsFromParameters(Iterable<Parameter> parameters) {
        return parameters.iter()
                .map((Parameter parameter) -> parameter.asDefinition())
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<Definition>());
    }

    public static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return CompilerUtils.parseValuesOrEmpty(state, params, (CompileState state1, String s) -> new Some<Tuple2<CompileState, Parameter>>(DefiningCompiler.parseParameterOrPlaceholder(state1, s)));
    }

    public static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, String input) {
        return DefiningCompiler.parseParameter(state, input).orElseGet(() -> new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
    }

    public static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, String input) {
        return CompilerUtils.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> DefiningCompiler.getCompileStateParameterTuple2(tuple))
                .or(() -> DefiningCompiler.parseDefinition(state, input).map((Tuple2<CompileState, Definition> tuple) -> new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
    }

    private static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
        return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
    }

    public static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return CompilerUtils.compileLast(Strings.strip(input), " ", (String beforeName, String name) -> {
            return CompilerUtils.compileSplit(beforeName, (beforeName0) -> {
                return CompilerUtils.splitFolded(Strings.strip(beforeName0), new TypeSeparatorFolder(), new LastSelector(" "));
            }, (String beforeType, String type) -> CompilerUtils.compileLast(Strings.strip(beforeType), "\n", (String annotationsString, String afterAnnotations) -> {
                var annotations = DefiningCompiler.parseAnnotations(annotationsString);
                return DefiningCompiler.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
            }).or(() -> DefiningCompiler.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name))).or(() -> DefiningCompiler.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name));
        });
    }

    public static List<String> parseAnnotations(String s) {
        return new FoldedDivider(new DecoratedFolder((DivideState state1, char c) -> new DelimitedFolder('\n').apply(state1, c))).divide(s)
                .map((String s2) -> Strings.strip(s2))
                .filter((String value) -> !Strings.isEmpty(value))
                .filter((String value) -> 1 <= Strings.length(value))
                .map((String value) -> Strings.sliceFrom(value, 1))
                .map((String s1) -> Strings.strip(s1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(
            CompileState state,
            List<String> annotations,
            String beforeType,
            String type,
            String name
    ) {
        return CompilerUtils.compileSuffix(Strings.strip(beforeType), ">", (String withoutTypeParamEnd) -> CompilerUtils.compileSplit(withoutTypeParamEnd, new LocatingSplitter("<", new FirstLocator()), (String beforeTypeParams, String typeParamsString) -> {
            var typeParams = divideValues(typeParamsString);
            return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, typeParams, DefiningCompiler.parseModifiers(beforeTypeParams), type, name);
        })).or(() -> {
            var divided = DefiningCompiler.parseModifiers(beforeType);
            return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
        });
    }

    public static List<String> parseModifiers(String beforeType) {
        return new FoldedDivider(new DecoratedFolder((DivideState state1, char c) -> new DelimitedFolder(' ').apply(state1, c))).divide(Strings.strip(beforeType))
                .map((String s) -> Strings.strip(s))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(
            CompileState state,
            List<String> annotations,
            List<String> typeParams,
            List<String> oldModifiers,
            String type,
            String name
    ) {
        return TypeCompiler.parseType(state, type).flatMap((Tuple2<CompileState, Type> typeTuple) -> {
            var newModifiers = DefiningCompiler.modifyModifiers(oldModifiers);
            var generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
            return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
        });
    }

    public static String joinParameters(Iterable<Definition> parameters) {
        return parameters.iter()
                .map((Definition definition) -> definition.generate())
                .map((String generated) -> "\n\t" + generated + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    private static List<String> modifyModifiers(List<String> oldModifiers) {
        if (oldModifiers.contains("static")) {
            return Lists.of("static");
        }
        return Lists.empty();
    }

    static List<String> divideValues(String input) {
        return new FoldedDivider(new DecoratedFolder(new ValueFolder())).divide(input)
                .map((String input1) -> Strings.strip(input1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }
}