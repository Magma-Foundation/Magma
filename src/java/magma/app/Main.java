package magma.app;

import jvm.api.collect.list.Lists;
import jvm.api.io.Console;
import jvm.api.io.Files;
import jvm.api.text.Characters;
import jvm.api.text.Strings;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.Type;
import magma.api.collect.Joiner;
import magma.api.collect.Queries;
import magma.api.collect.head.HeadedQuery;
import magma.api.collect.head.RangeHead;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.FunctionSegment;
import magma.app.compile.ImmutableCompileState;
import magma.app.compile.Import;
import magma.app.compile.define.Definition;
import magma.app.compile.define.FunctionHeader;
import magma.app.compile.define.Parameter;
import magma.app.compile.text.DivideState;
import magma.app.compile.text.Placeholder;
import magma.app.compile.text.Whitespace;
import magma.app.compile.type.ArrayType;
import magma.app.compile.type.BooleanType;
import magma.app.compile.type.FunctionType;
import magma.app.compile.type.PrimitiveType;
import magma.app.compile.type.SliceType;
import magma.app.compile.type.TemplateType;
import magma.app.compile.type.VariadicType;
import magma.app.compile.value.AccessNode;
import magma.app.compile.value.Argument;
import magma.app.compile.value.Caller;
import magma.app.compile.value.ConstructionCaller;
import magma.app.compile.value.ConstructorHeader;
import magma.app.compile.value.InvokableNode;
import magma.app.compile.value.LambdaNode;
import magma.app.compile.value.NotNode;
import magma.app.compile.value.OperationNode;
import magma.app.compile.value.StringNode;
import magma.app.compile.value.SymbolNode;
import magma.app.compile.value.Value;
import magma.app.io.Location;
import magma.app.io.Platform;
import magma.app.io.Source;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class Main {
    public static void main() {
        final Path sourceDirectory = Files.get(".", "src", "java");
        sourceDirectory.walk()
                .match(
                        (List<Path> children) -> Main.runWithChildren(children, sourceDirectory),
                        (IOError value) -> new Some<IOError>(value))
                .map((IOError error) -> error.display())
                .ifPresent((String displayed) -> Console.printErrLn(displayed));
    }

    private static Option<IOError> runWithChildren(final List<Path> children, final Path sourceDirectory) {
        final List<Source> sources = children.query()
                .filter((Path source) -> source.endsWith(".java"))
                .map((Path child) -> new Source(sourceDirectory, child))
                .collect(new ListCollector<Source>());

        final CompileState initial = sources.query().foldWithInitial(ImmutableCompileState.createInitial(), (CompileState state, Source source) -> state.addSource(source));

        return sources.query()
                .foldWithInitial(Main.createInitialState(initial), (Tuple2<CompileState, Option<IOError>> current, Source source1) -> Main.foldChild(current.left(), current.right(), source1))
                .right();
    }

    private static Tuple2<CompileState, Option<IOError>> createInitialState(final CompileState state) {
        return new Tuple2Impl<CompileState, Option<IOError>>(state, new None<IOError>());
    }

    private static Tuple2<CompileState, Option<IOError>> foldChild(final CompileState state, final Option<IOError> maybeError, final Source source) {
        if (maybeError.isPresent()) {
            return new Tuple2Impl<CompileState, Option<IOError>>(state, maybeError);
        }

        return Main.runWithSource(state, source);
    }

    private static Tuple2<CompileState, Option<IOError>> runWithSource(final CompileState state, final Source source) {
        return source.read().match(
                (String input) -> Main.getCompileStateOptionTuple2(state, source, input),
                (IOError value) -> new Tuple2Impl<CompileState, Option<IOError>>(state, new Some<IOError>(value)));
    }

    private static Tuple2Impl<CompileState, Option<IOError>> getCompileStateOptionTuple2(final CompileState state, final Source source, final String input) {
        final Tuple2<CompileState, Option<IOError>> typeScriptTuple = Main.compileAndWrite(state, source, input, Platform.TypeScript);
        final Tuple2<CompileState, Option<IOError>> magmaTuple = Main.compileAndWrite(typeScriptTuple.left(), source, input, Platform.Magma);
        final Tuple2<CompileState, Option<IOError>> windowsTuple = Main.compileAndWrite(magmaTuple.left(), source, input, Platform.Windows);

        return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple.left(), typeScriptTuple.right()
                .or(() -> magmaTuple.right())
                .or(() -> windowsTuple.right()));
    }

    private static Tuple2<CompileState, Option<IOError>> compileAndWrite(final CompileState state, final Source source, final String input, final Platform platform) {
        final CompileState state1 = state.withLocation(source.computeLocation()).withPlatform(platform);
        final Tuple2Impl<CompileState, Map<String, String>> output = Main.compileRoot(state1, source, input);

        final Location location = output.left().findCurrentLocation().orElse(new Location(Lists.empty(), ""));
        final Path targetDirectory = Files.get(".", "src", platform.root);
        final Path targetParent = targetDirectory.resolveChildSegments(location.namespace());
        if (!targetParent.exists()) {
            final Option<IOError> maybeError = targetParent.createDirectories();
            if (maybeError.isPresent()) {
                return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), maybeError);
            }
        }

        final Option<IOError> initial = new None<IOError>();
        final Option<IOError> ioErrorOption1 = Queries.fromArray(platform.extension).foldWithInitial(initial, (final Option<IOError> ioErrorOption, final String extension) -> {
            final Path target = targetParent.resolveChild(location.name() + "." + extension);
            return ioErrorOption.or(() -> target.writeString(output.right().get(extension)));
        });

        return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), ioErrorOption1);
    }

    private static Tuple2Impl<CompileState, Map<String, String>> compileRoot(final CompileState state, final Source source, final String input) {
        final Tuple2<CompileState, String> statementsTuple = Main.compileStatements(state, input, Main::compileRootSegment);
        final CompileState statementsState = statementsTuple.left();

        final String imports = statementsState.imports().query()
                .map((Import anImport) -> anImport.generate(state.platform()))
                .collect(new Joiner(""))
                .orElse("");

        final CompileState compileState = statementsState.clearImports().clear();
        final String withMain = Main.createMain(source);

        final HashMap<String, String> entries = new HashMap<String, String>();
        final Platform platform = state.platform();
        if (Platform.Windows == platform) {
            final String value = source.computeNamespace().query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + source.computeName();
            entries.put(Platform.Windows.extension[0], Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"));
            entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + source.computeName() + ".h\"") + statementsState.join() + statementsTuple.right() + withMain);
        }
        else {
            entries.put(platform.extension[0], imports + statementsState.join() + statementsTuple.right() + withMain);
        }

        return new Tuple2Impl<>(compileState, entries);
    }

    private static String generateDirective(final String content) {
        return "#" + content + "\n";
    }

    private static String createMain(final Source source) {
        if (Strings.equalsTo(source.computeName(), "Main")) {
            return "Main.main();";
        }
        return "";
    }

    private static Tuple2<CompileState, String> compileStatements(final CompileState state, final String input, final BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return Main.compileAll(state, input, Main::foldStatements, mapper, Main::mergeStatements);
    }

    private static Tuple2<CompileState, String> compileAll(final CompileState state, final String input, final BiFunction<DivideState, Character, DivideState> folder, final BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, final BiFunction<String, String, String> merger) {
        final Tuple2<CompileState, List<String>> folded = Main.parseAll(state, input, folder, (CompileState state1, String s) -> new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s))).orElse(new Tuple2Impl<CompileState, List<String>>(state, Lists.empty()));
        return new Tuple2Impl<CompileState, String>(folded.left(), Main.generateAll(folded.right(), merger));
    }

    public static String generateAll(final List<String> elements, final BiFunction<String, String, String> merger) {
        return elements.query()
                .foldWithInitial("", merger);
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseAll(final CompileState state, final String input, final BiFunction<DivideState, Character, DivideState> folder, final BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> biFunction) {
        return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (final Option<Tuple2<CompileState, List<T>>> maybeCurrent, final String segment) -> maybeCurrent.flatMap((final Tuple2<CompileState, List<T>> current) -> {
            final CompileState currentState = current.left();
            final List<T> currentElement = current.right();

            return biFunction.apply(currentState, segment).map((final Tuple2<CompileState, T> mappedTuple) -> {
                final CompileState mappedState = mappedTuple.left();
                final T mappedElement = mappedTuple.right();
                return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
            });
        }));
    }

    private static String mergeStatements(final String cache, final String element) {
        return cache + element;
    }

    private static List<String> divide(final String input, final BiFunction<DivideState, Character, DivideState> folder) {
        DivideState current = DivideState.createInitial(input);

        while (true) {
            final Tuple2<Boolean, Tuple2<DivideState, Character>> poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, Character>(current, '\0'));
            if (!poppedTuple0.left()) {
                break;
            }

            final Tuple2<DivideState, Character> poppedTuple = poppedTuple0.right();
            final DivideState poppedState = poppedTuple.left();
            final Character popped = poppedTuple.right();

            current = Main.foldSingleQuotes(poppedState, popped)
                    .or(() -> Main.foldDoubleQuotes(poppedState, popped))
                    .orElseGet(() -> folder.apply(poppedState, popped));
        }

        return current.advance().segments();
    }

    private static Option<DivideState> foldDoubleQuotes(final DivideState state, final char c) {
        if ('\"' != c) {
            return new None<DivideState>();
        }

        DivideState appended = state.append(c);
        while (true) {
            final Tuple2<Boolean, Tuple2<DivideState, Character>> maybeTuple = appended.popAndAppendToTuple()
                    .toTuple(new Tuple2Impl<DivideState, Character>(appended, '\0'));

            if (!maybeTuple.left()) {
                break;
            }

            final Tuple2<DivideState, Character> tuple = maybeTuple.right();
            appended = tuple.left();

            if ('\\' == tuple.right()) {
                appended = appended.popAndAppendToOption().orElse(appended);
            }
            if ('\"' == tuple.right()) {
                break;
            }
        }
        return new Some<DivideState>(appended);
    }

    private static Option<DivideState> foldSingleQuotes(final DivideState state, final char c) {
        if ('\'' != c) {
            return new None<DivideState>();
        }

        return state.append(c)
                .popAndAppendToTuple()
                .flatMap(Main::foldEscaped)
                .flatMap((DivideState state1) -> state1.popAndAppendToOption());
    }

    private static Option<DivideState> foldEscaped(final Tuple2<DivideState, Character> tuple) {
        final DivideState state = tuple.left();
        final Character c = tuple.right();

        if ('\\' == c) {
            return state.popAndAppendToOption();
        }

        return new Some<DivideState>(state);
    }

    private static DivideState foldStatements(final DivideState state, final char c) {
        final DivideState appended = state.append(c);
        if (';' == c && appended.isLevel()) {
            return appended.advance();
        }

        if ('}' == c && appended.isShallow()) {
            return appended.advance().exit();
        }

        if ('{' == c || '(' == c) {
            return appended.enter();
        }

        if ('}' == c || ')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static Tuple2<CompileState, String> compileRootSegment(final CompileState state, final String input) {
        return Main.compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileNamespaced,
                Main.createStructureRule("class ", "class "),
                Main.createStructureRule("interface ", "interface "),
                Main.createStructureRule("record ", "class "),
                Main.createStructureRule("enum ", "class ")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureRule(final String sourceInfix, final String targetInfix) {
        return (final CompileState state, final String input1) -> Main.compileFirst(input1, sourceInfix, (final String beforeInfix, final String afterInfix) -> Main.compileFirst(afterInfix, "{", (final String beforeContent, final String withEnd) -> Main.compileSuffix(Strings.strip(withEnd), "}", (final String inputContent) -> Main.compileLast(beforeInfix, "\n", (final String s, final String s2) -> {
            final List<String> annotations = Main.parseAnnotations(s);
            if (annotations.contains("Actual", Strings::equalsTo)) {
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
            }

            return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
        }).or(() -> {
            final List<String> modifiers = Main.parseModifiers(beforeContent);
            return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
        }))));
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithImplementing(final CompileState state, final List<String> annotations, final List<String> modifiers, final String targetInfix, final String beforeContent, final String content) {
        return Main.compileLast(beforeContent, " implements ", (final String s, final String s2) -> Main.parseType(state, s2).flatMap((final Tuple2<CompileState, Type> implementingTuple) -> Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content))).or(() -> Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content));
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithExtends(final CompileState state, final List<String> annotations, final List<String> modifiers, final String targetInfix, final String beforeContent, final Option<Type> maybeImplementing, final String inputContent) {
        return Main.compileFirst(beforeContent, " extends ", (String beforeExtends, String afterExtends) -> Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<String>(afterExtends), maybeImplementing, inputContent)).or(() -> Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<String>(), maybeImplementing, inputContent));
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithParameters(final CompileState state, final List<String> annotations, final List<String> modifiers, final String targetInfix, final String beforeContent, final Option<String> maybeSuperType, final Option<Type> maybeImplementing, final String inputContent) {
        return Main.compileFirst(beforeContent, "(", (final String rawName, final String withParameters) -> Main.compileFirst(withParameters, ")", (final String parametersString, final String _) -> {
            final String name = Strings.strip(rawName);

            final Tuple2<CompileState, List<Parameter>> parametersTuple = Main.parseParameters(state, parametersString);
            final List<Definition> parameters = Main.retainDefinitionsFromParameters(parametersTuple.right());

            return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
        })).or(() -> Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType));
    }

    private static List<Definition> retainDefinitionsFromParameters(final List<Parameter> parameters) {
        return parameters.query()
                .map((Parameter parameter) -> parameter.asDefinition())
                .flatMap(Queries::fromOption)
                .collect(new ListCollector<Definition>());
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithTypeParams(final CompileState state, final String infix, final String content, final String beforeParams, final List<Definition> parameters, final Option<Type> maybeImplementing, final List<String> annotations, final List<String> modifiers, final Option<String> maybeSuperType) {
        return Main.compileSuffix(Strings.strip(beforeParams), ">", (final String withoutTypeParamEnd) -> Main.compileFirst(withoutTypeParamEnd, "<", (final String name, final String typeParamsString) -> {
            final List<String> typeParams = Main.divideValues(typeParamsString);
            return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
        })).or(() -> Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType));
    }

    private static Option<Tuple2<CompileState, String>> assembleStructure(
            final CompileState state,
            final List<String> annotations,
            final List<String> oldModifiers,
            final String infix,
            final String rawName,
            final List<String> typeParams,
            final List<Definition> parameters,
            final Option<Type> maybeImplementing,
            final String content,
            final Option<String> maybeSuperType
    ) {
        final String name = Strings.strip(rawName);
        if (!Main.isSymbol(name)) {
            return new None<Tuple2<CompileState, String>>();
        }

        final Tuple2<CompileState, String> outputContentTuple = Main.compileStatements(state.pushStructureName(name), content, Main::compileClassSegment);
        final CompileState outputContentState = outputContentTuple.left().popStructureName();
        final String outputContent = outputContentTuple.right();

        final Platform platform = outputContentState.platform();
        final String constructorString = Main.generateConstructorFromRecordParameters(parameters, platform);
        final String joinedTypeParams = Joiner.joinOrEmpty(typeParams, ", ", "<", ">");
        final String implementingString = Main.generateImplementing(maybeImplementing);
        final List<String> newModifiers = Main.modifyModifiers0(oldModifiers);

        final String joinedModifiers = newModifiers.query()
                .map((String value) -> value + " ")
                .collect(Joiner.empty())
                .orElse("");

        if (annotations.contains("Namespace", Strings::equalsTo)) {
            final String actualInfix = "interface ";
            final String newName = name + "Instance";

            final String generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters, platform) + constructorString + outputContent + "\n}\n";
            final CompileState withNewLocation = outputContentState.append(generated).mapLocation((Location location) -> new Location(location.namespace(), location.name() + "Instance"));

            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(withNewLocation, ""));
        }
        else {
            final String extendsString = maybeSuperType.map((String inner) -> " extends " + inner).orElse("");
            final String infix1 = Main.retainStruct(infix, outputContentState);

            final String generated = joinedModifiers + infix1 + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters, platform) + constructorString + outputContent + "\n}\n";
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(outputContentState.append(generated), ""));
        }
    }

    private static String retainStruct(final String infix, final CompileState outputContentState) {
        if (outputContentState.isPlatform(Platform.Magma)) {
            return "struct ";
        }
        return infix;
    }

    private static List<String> modifyModifiers0(final List<String> oldModifiers) {
        if (oldModifiers.contains("public", Strings::equalsTo)) {
            return Lists.of("export");
        }
        return Lists.empty();
    }

    private static String generateImplementing(final Option<Type> maybeImplementing) {
        return maybeImplementing.map((Type type) -> type.generate())
                .map((String inner) -> " implements " + inner)
                .orElse("");
    }

    private static String generateConstructorFromRecordParameters(final List<Definition> parameters, final Platform platform) {
        return parameters.query()
                .map((Definition definition) -> definition.generate(platform))
                .collect(new Joiner(", "))
                .map((String generatedParameters) -> Main.generateConstructorWithParameterString(parameters, generatedParameters))
                .orElse("");
    }

    private static String generateConstructorWithParameterString(final List<Definition> parameters, final String parametersString) {
        final String constructorAssignments = Main.generateConstructorAssignments(parameters);

        return "\n\tconstructor (" + parametersString + ") {" +
                constructorAssignments +
                "\n\t}";
    }

    private static String generateConstructorAssignments(final List<Definition> parameters) {
        return parameters.query()
                .map((Definition definition) -> "\n\t\tthis." + definition.name() + " = " + definition.name() + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    private static String joinParameters(final List<Definition> parameters, final Platform platform) {
        return parameters.query()
                .map((Definition definition) -> definition.generate(platform))
                .map((String generated) -> "\n\t" + generated + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    private static Option<Tuple2<CompileState, String>> compileNamespaced(final CompileState state, final String input) {
        final String stripped = Strings.strip(input);
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
        }

        return new None<>();
    }

    private static Tuple2<CompileState, String> compileOrPlaceholder(
            final CompileState state,
            final String input,
            final List<BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>>> rules
    ) {
        return Main.or(state, input, rules).orElseGet(() -> new Tuple2Impl<CompileState, String>(state, Main.generatePlaceholder(input)));
    }

    private static <T> Option<Tuple2<CompileState, T>> or(
            final CompileState state,
            final String input,
            final List<BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>> rules
    ) {
        return rules.query()
                .map((BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) -> Main.getApply(state, input, rule))
                .flatMap(Queries::fromOption)
                .next();
    }

    private static <T> Option<Tuple2<CompileState, T>> getApply(final CompileState state, final String input, final BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) {
        return rule.apply(state, input);
    }

    private static Tuple2<CompileState, String> compileClassSegment(final CompileState state1, final String input1) {
        return Main.compileOrPlaceholder(state1, input1, Lists.of(
                Main::compileWhitespace,
                Main.createStructureRule("class ", "class "),
                Main.createStructureRule("interface ", "interface "),
                Main.createStructureRule("record ", "class "),
                Main.createStructureRule("enum ", "class "),
                Main::compileMethod,
                Main::compileFieldDefinition
        ));
    }

    private static Option<Tuple2<CompileState, String>> compileMethod(final CompileState state, final String input) {
        return Main.compileFirst(input, "(", (final String beforeParams, final String withParams) -> {
            final String strippedBeforeParams = Strings.strip(beforeParams);
            return Main.compileLast(strippedBeforeParams, " ", (final String _, final String name) -> {
                if (state.hasLastStructureNameOf(name)) {
                    return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> {
                if (state.hasLastStructureNameOf(strippedBeforeParams)) {
                    return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> Main.parseDefinition(state, beforeParams)
                    .flatMap((Tuple2<CompileState, Definition> tuple) -> Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)));
        });
    }

    private static <S extends FunctionHeader<S>> Option<Tuple2<CompileState, String>> compileMethodWithBeforeParams(
            final CompileState state,
            final FunctionHeader<S> header,
            final String withParams
    ) {
        return Main.compileFirst(withParams, ")", (final String params, final String afterParams) -> {
            final Tuple2<CompileState, List<Parameter>> parametersTuple = Main.parseParameters(state, params);

            final CompileState parametersState = parametersTuple.left();
            final List<Parameter> parameters = parametersTuple.right();
            final List<Definition> definitions = Main.retainDefinitionsFromParameters(parameters);

            final FunctionHeader<S> newHeader = Main.retainDef(header, parametersState);

            if (newHeader.hasAnnotation("Actual")) {
                final S aStatic = newHeader.removeModifier("static");
                final FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new None<>());
                final String generate = sFunctionSegment.generate(parametersState.platform(), "\n\t");
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, generate));
            }

            return Main.compilePrefix(Strings.strip(afterParams), "{", (final String withoutContentStart) -> Main.compileSuffix(Strings.strip(withoutContentStart), "}", (final String withoutContentEnd) -> {
                final CompileState compileState1 = parametersState.enterDepth();
                final CompileState compileState = compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth();

                final Tuple2<CompileState, String> statementsTuple = Main.compileFunctionStatements(compileState.defineAll(definitions), withoutContentEnd);
                final CompileState compileState2 = statementsTuple.left().exitDepth();

                final String indent = compileState2.createIndent();
                final CompileState exited = compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth();
                final FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new Some<>(statementsTuple.right()));
                final String generated = sFunctionSegment.generate(parametersState.platform(), indent);

                if (exited.isPlatform(Platform.Windows)) {
                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(exited.addFunction(generated), ""));
                }

                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(exited, generated));
            })).or(() -> {
                if (Strings.equalsTo(";", Strings.strip(afterParams))) {
                    final FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new None<>());
                    final String generate = sFunctionSegment.generate(parametersState.platform(), "\n\t");
                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, generate));
                }

                return new None<Tuple2<CompileState, String>>();
            });
        });
    }

    private static <S extends FunctionHeader<S>> FunctionHeader<S> retainDef(final FunctionHeader<S> header, final CompileState parametersState) {
        if (parametersState.isPlatform(Platform.Magma)) {
            return header.addModifierLast("def").removeModifier("mut");
        }

        return header;
    }

    private static Tuple2<CompileState, List<Parameter>> parseParameters(final CompileState state, final String params) {
        return Main.parseValuesOrEmpty(state, params, (CompileState state1, String s) -> new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
    }

    private static Tuple2<CompileState, String> compileFunctionStatements(final CompileState state, final String input) {
        return Main.compileStatements(state, input, Main::compileFunctionSegment);
    }

    private static Tuple2<CompileState, String> compileFunctionSegment(final CompileState state, final String input) {
        return Main.compileOrPlaceholder(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileEmptySegment,
                Main::compileBlock,
                Main::compileFunctionStatement,
                Main::compileReturnWithoutSuffix
        ));
    }

    private static Option<Tuple2<CompileState, String>> compileEmptySegment(final CompileState state, final String input) {
        if (Strings.equalsTo(";", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ";"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithoutSuffix(final CompileState state1, final String input1) {
        return Main.compileReturn(input1, (String withoutPrefix) -> Main.compileValue(state1, withoutPrefix))
                .map((Tuple2<CompileState, String> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), state1.createIndent() + tuple.right()));
    }

    private static Option<Tuple2<CompileState, String>> compileBlock(final CompileState state, final String input) {
        return Main.compileSuffix(Strings.strip(input), "}", (final String withoutEnd) -> Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main::foldBlockStarts), (final String beforeContentWithEnd, final String content) -> Main.compileSuffix(beforeContentWithEnd, "{", (final String beforeContent) -> Main.compileBlockHeader(state, beforeContent).flatMap((final Tuple2<CompileState, String> headerTuple) -> {
            final Tuple2<CompileState, String> contentTuple = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);

            final String indent = state.createIndent();
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
        }))));
    }

    private static DivideState foldBlockStarts(final DivideState state, final char c) {
        final DivideState appended = state.append(c);
        if ('{' == c) {
            final DivideState entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if ('}' == c) {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple2<CompileState, String>> compileBlockHeader(final CompileState state, final String input) {
        return Main.or(state, input, Lists.of(
                Main.createConditionalRule("if"),
                Main.createConditionalRule("while"),
                Main::compileElse
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createConditionalRule(final String prefix) {
        return (CompileState state1, String input1) -> Main.compilePrefix(Strings.strip(input1), prefix, (final String withoutPrefix) -> {
            final String strippedCondition = Strings.strip(withoutPrefix);
            return Main.compilePrefix(strippedCondition, "(", (final String withoutConditionStart) -> Main.compileSuffix(withoutConditionStart, ")", (final String withoutConditionEnd) -> {
                final Tuple2<CompileState, String> tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), prefix + " (" + tuple.right() + ") "));
            }));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileElse(final CompileState state, final String input) {
        if (Strings.equalsTo("else", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "else "));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static Option<Tuple2<CompileState, String>> compileFunctionStatement(final CompileState state, final String input) {
        return Main.compileSuffix(Strings.strip(input), ";", (final String withoutEnd) -> {
            final Tuple2<CompileState, String> valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
        });
    }

    private static Tuple2<CompileState, String> compileFunctionStatementValue(final CompileState state, final String withoutEnd) {
        return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(
                Main::compileReturnWithValue,
                Main::compileAssignment,
                (CompileState state1, String input) -> Main.parseInvokable(state1, input).map((Tuple2<CompileState, Value> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate(tuple.left().platform()))),
                Main.createPostRule("++"),
                Main.createPostRule("--"),
                Main::compileBreak
        ));
    }

    private static Option<Tuple2<CompileState, String>> compileBreak(final CompileState state, final String input) {
        if (Strings.equalsTo("break", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "break"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createPostRule(final String suffix) {
        return (CompileState state1, String input) -> Main.compileSuffix(Strings.strip(input), suffix, (final String child) -> {
            final Tuple2<CompileState, String> tuple = Main.compileValueOrPlaceholder(state1, child);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right() + suffix));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithValue(final CompileState state, final String input) {
        return Main.compileReturn(input, (String value1) -> Main.compileValue(state, value1));
    }

    private static Option<Tuple2<CompileState, String>> compileReturn(final String input, final Function<String, Option<Tuple2<CompileState, String>>> mapper) {
        return Main.compilePrefix(Strings.strip(input), "return ", (final String value) -> mapper.apply(value).flatMap((final Tuple2<CompileState, String> tuple) -> new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), "return " + tuple.right()))));
    }

    private static Option<Tuple2<CompileState, Value>> parseInvokable(final CompileState state, final String input) {
        return Main.compileSuffix(Strings.strip(input), ")", (final String withoutEnd) -> Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main::foldInvocationStarts), (final String callerWithArgStart, final String args) -> Main.compileSuffix(callerWithArgStart, "(", (final String callerString) -> Main.compilePrefix(Strings.strip(callerString), "new ", (final String type) -> Main.compileType(state, type).flatMap((final Tuple2<CompileState, String> callerTuple) -> {
            final CompileState callerState = callerTuple.left();
            final String caller = callerTuple.right();
            return Main.assembleInvokable(callerState, new ConstructionCaller(caller, callerState.platform()), args);
        })).or(() -> Main.parseValue(state, callerString).flatMap((final Tuple2<CompileState, Value> callerTuple) -> Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args))))));
    }

    private static Option<Tuple2<String, String>> splitFoldedLast(final String input, final String delimiter, final BiFunction<DivideState, Character, DivideState> folder) {
        return Main.splitFolded(input, folder, (List<String> divisions1) -> Main.selectLast(divisions1, delimiter));
    }

    private static Option<Tuple2<String, String>> splitFolded(
            final String input,
            final BiFunction<DivideState, Character, DivideState> folder,
            final Function<List<String>, Option<Tuple2<String, String>>> selector
    ) {
        final List<String> divisions = Main.divide(input, folder);
        if (2 > divisions.size()) {
            return new None<Tuple2<String, String>>();
        }

        return selector.apply(divisions);
    }

    private static Option<Tuple2<String, String>> selectLast(final List<String> divisions, final String delimiter) {
        final List<String> beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
        final String last = divisions.findLast().orElse("");

        final String joined = beforeLast.query()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(joined, last));
    }

    private static DivideState foldInvocationStarts(final DivideState state, final char c) {
        final DivideState appended = state.append(c);
        if ('(' == c) {
            final DivideState entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if (')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static Option<Tuple2<CompileState, Value>> assembleInvokable(final CompileState state, final Caller oldCaller, final String argsString) {
        return Main.parseValues(state, argsString, (CompileState state1, String s) -> Main.parseArgument(state1, s)).flatMap((final Tuple2<CompileState, List<Argument>> argsTuple) -> {
            final CompileState argsState = argsTuple.left();
            final List<Value> args = Main.retain(argsTuple.right(), (Argument argument) -> argument.toValue());

            final Caller newCaller = Main.transformCaller(argsState, oldCaller);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new InvokableNode(newCaller, args)));
        });
    }

    private static Caller transformCaller(final CompileState state, final Caller oldCaller) {
        return oldCaller.findChild().flatMap((final Value parent) -> {
            final Type parentType = parent.resolve(state);
            if (parentType.isFunctional()) {
                return new Some<Caller>(parent);
            }

            return new None<Caller>();
        }).orElse(oldCaller);
    }

    private static <T, R> List<R> retain(final List<T> args, final Function<T, Option<R>> mapper) {
        return args.query()
                .map(mapper)
                .flatMap(Queries::fromOption)
                .collect(new ListCollector<R>());
    }

    private static Option<Tuple2<CompileState, Argument>> parseArgument(final CompileState state1, final String input) {
        return Main.parseValue(state1, input)
                .map((Tuple2<CompileState, Value> tuple) -> new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
    }

    private static Option<Tuple2<CompileState, String>> compileAssignment(final CompileState state, final String input) {
        return Main.compileFirst(input, "=", (final String destination, final String source) -> {
            final Tuple2<CompileState, String> sourceTuple = Main.compileValueOrPlaceholder(state, source);

            final Tuple2<CompileState, String> destinationTuple = Main.compileValue(sourceTuple.left(), destination)
                    .or(() -> Main.parseDefinition(sourceTuple.left(), destination).map((final Tuple2<CompileState, Definition> definitionTuple) -> {
                        final CompileState definitionState = definitionTuple.left();
                        final Definition definition = definitionTuple.right();
                        final Definition let = Main.attachLet(definitionState, definition);
                        final String generate = let.generate(definitionState.platform());
                        return new Tuple2Impl<CompileState, String>(definitionState, generate);
                    }))
                    .orElseGet(() -> new Tuple2Impl<CompileState, String>(sourceTuple.left(), Main.generatePlaceholder(destination)));

            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
        });
    }

    private static Definition attachLet(final CompileState definitionState, final Definition definition) {
        final Definition let;
        if (definitionState.isPlatform(Platform.Windows)) {
            let = definition;
        }
        else {
            let = definition.addModifierLast("let");
        }
        return let;
    }

    private static Tuple2<CompileState, String> compileValueOrPlaceholder(final CompileState state, final String input) {
        return Main.compileValue(state, input).orElseGet(() -> new Tuple2Impl<CompileState, String>(state, Main.generatePlaceholder(input)));
    }

    private static Option<Tuple2<CompileState, String>> compileValue(final CompileState state, final String input) {
        return Main.parseValue(state, input).map((final Tuple2<CompileState, Value> tuple) -> {
            final String generated = tuple.right().generate(tuple.left().platform());
            return new Tuple2Impl<CompileState, String>(tuple.left(), generated);
        });
    }

    private static Option<Tuple2<CompileState, Value>> parseValue(final CompileState state, final String input) {
        return Main.or(state, input, Lists.of(
                Main::parseLambda,
                Main.createOperatorRule("+"),
                Main.createOperatorRule("-"),
                Main.createOperatorRule("<="),
                Main.createOperatorRule("<"),
                Main.createOperatorRule("&&"),
                Main.createOperatorRule("||"),
                Main.createOperatorRule(">"),
                Main.createOperatorRule(">="),
                Main::parseInvokable,
                Main.createAccessRule("."),
                Main.createAccessRule("::"),
                Main::parseSymbol,
                Main::parseNot,
                Main::parseNumber,
                Main.createOperatorRuleWithDifferentInfix("==", "==="),
                Main.createOperatorRuleWithDifferentInfix("!=", "!=="),
                Main.createTextRule("\""),
                Main.createTextRule("'")
        ));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createTextRule(final String slice) {
        return (final CompileState state1, final String input1) -> {
            final String stripped = Strings.strip(input1);
            return Main.compilePrefix(stripped, slice, (final String s) -> Main.compileSuffix(s, slice, (String s1) -> new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringNode(s1)))));
        };
    }

    private static Option<Tuple2<CompileState, Value>> parseNot(final CompileState state, final String input) {
        return Main.compilePrefix(Strings.strip(input), "!", (final String withoutPrefix) -> {
            final Tuple2<CompileState, String> childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
            final CompileState childState = childTuple.left();
            final String child = "!" + childTuple.right();
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new NotNode(child)));
        });
    }

    private static Option<Tuple2<CompileState, Value>> parseLambda(final CompileState state, final String input) {
        return Main.compileFirst(input, "->", (final String beforeArrow, final String afterArrow) -> {
            final String strippedBeforeArrow = Strings.strip(beforeArrow);
            return Main.compilePrefix(strippedBeforeArrow, "(", (final String withoutStart) -> Main.compileSuffix(withoutStart, ")", (final String withoutEnd) -> Main.parseValues(state, withoutEnd, (CompileState state1, String s) -> Main.parseParameter(state1, s)).flatMap((final Tuple2<CompileState, List<Parameter>> paramNames) -> Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow))));
        });
    }

    private static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(final CompileState state, final List<Definition> paramNames, final String afterArrow) {
        final String strippedAfterArrow = Strings.strip(afterArrow);
        return Main.compilePrefix(strippedAfterArrow, "{", (final String withoutContentStart) -> Main.compileSuffix(withoutContentStart, "}", (final String withoutContentEnd) -> {
            final Tuple2<CompileState, String> statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
            final CompileState statementsState = statementsTuple.left();
            final String statements = statementsTuple.right();

            final CompileState exited = statementsState.exitDepth();
            final String content = "{" + statements + exited.createIndent() + "}";
            if (exited.isPlatform(Platform.Windows)) {
                return Main.assembleLambdaWithContent(exited, paramNames, content);
            }

            return Main.getSome(exited, paramNames, content);
        })).or(() -> Main.compileValue(state, strippedAfterArrow).flatMap((final Tuple2<CompileState, String> tuple) -> {
            final CompileState state1 = tuple.left();
            final String content = tuple.right();
            if (state1.isPlatform(Platform.Windows)) {
                return Main.assembleLambdaWithContent(state1, paramNames, "\n\treturn " + content + ";");
            }

            return Main.getSome(state1, paramNames, content);
        }));
    }

    private static Some<Tuple2<CompileState, Value>> getSome(final CompileState state, final List<Definition> parameters, final String content) {
        return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new LambdaNode(parameters, content)));
    }

    private static Some<Tuple2<CompileState, Value>> assembleLambdaWithContent(final CompileState state, final List<Definition> parameters, final String content) {
        final Definition lambdaDefinition = new Definition(PrimitiveType.Auto, state.functionName());
        final FunctionSegment<Definition> value = new FunctionSegment<Definition>(lambdaDefinition, parameters, new Some<>(content));
        return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state.addFunction(value.generate(state.platform(), "\n")), new SymbolNode("lambdaDefinition")));
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createOperatorRule(final String infix) {
        return Main.createOperatorRuleWithDifferentInfix(infix, infix);
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createAccessRule(final String infix) {
        return (CompileState state, String input) -> Main.compileLast(input, infix, (final String childString, final String rawProperty) -> {
            final String property = Strings.strip(rawProperty);
            if (!Main.isSymbol(property)) {
                return new None<Tuple2<CompileState, Value>>();
            }

            return Main.parseValue(state, childString).flatMap((final Tuple2<CompileState, Value> childTuple) -> {
                final CompileState childState = childTuple.left();
                final Value child = childTuple.right();
                return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessNode(child, property)));
            });
        });
    }

    private static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createOperatorRuleWithDifferentInfix(final String sourceInfix, final String targetInfix) {
        return (final CompileState state1, final String input1) -> Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (List<String> divisions) -> Main.selectFirst(divisions, sourceInfix)), (final String leftString, final String rightString) -> Main.parseValue(state1, leftString).flatMap((final Tuple2<CompileState, Value> leftTuple) -> Main.parseValue(leftTuple.left(), rightString).flatMap((final Tuple2<CompileState, Value> rightTuple) -> {
            final Value left = leftTuple.right();
            final Value right = rightTuple.right();
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new OperationNode(left, targetInfix, right)));
        })));
    }

    private static Option<Tuple2<String, String>> selectFirst(final List<String> divisions, final String delimiter) {
        final String first = divisions.findFirst().orElse("");
        final String afterFirst = divisions.subList(1, divisions.size()).orElse(divisions)
                .query()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(first, afterFirst));
    }

    private static BiFunction<DivideState, Character, DivideState> foldOperator(final String infix) {
        return (final DivideState state, final Character c) -> {
            if (c == Strings.charAt(infix, 0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
                final int length = Strings.length(infix) - 1;
                int counter = 0;
                DivideState current = state;
                while (counter < length) {
                    counter++;

                    current = current.pop().map((Tuple2<DivideState, Character> tuple) -> tuple.left()).orElse(current);
                }
                return current.advance();
            }

            return state.append(c);
        };
    }

    private static Option<Tuple2<CompileState, Value>> parseNumber(final CompileState state, final String input) {
        final String stripped = Strings.strip(input);
        if (Main.isNumber(stripped)) {
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    private static boolean isNumber(final String input) {
        final HeadedQuery<Integer> query = new HeadedQuery<Integer>(new RangeHead(Strings.length(input)));
        return query.map((Integer index) -> input.charAt(index)).allMatch((Character c) -> Characters.isDigit(c));
    }

    private static Option<Tuple2<CompileState, Value>> parseSymbol(final CompileState state, final String input) {
        final String stripped = Strings.strip(input);
        if (Main.isSymbol(stripped)) {
            final CompileState withImport = state.addResolvedImportFromCache(stripped);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new SymbolNode(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    private static boolean isSymbol(final String input) {
        final HeadedQuery<Integer> query = new HeadedQuery<Integer>(new RangeHead(Strings.length(input)));
        return query.allMatch((Integer index) -> Main.isSymbolChar(index, Strings.charAt(input, index)));
    }

    private static boolean isSymbolChar(final int index, final char c) {
        return '_' == c
                || Characters.isLetter(c)
                || (0 != index && Characters.isDigit(c));
    }

    private static <T> Option<Tuple2<CompileState, T>> compilePrefix(
            final String input,
            final String infix,
            final Function<String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        if (!input.startsWith(infix)) {
            return new None<Tuple2<CompileState, T>>();
        }

        final String slice = Strings.sliceFrom(input, Strings.length(infix));
        return mapper.apply(slice);
    }

    private static Option<Tuple2<CompileState, String>> compileWhitespace(final CompileState state, final String input) {
        return Main.parseWhitespace(state, input).map((final Tuple2<CompileState, Whitespace> tuple) -> {
            final String generate = tuple.right().generate(tuple.left().platform());
            return new Tuple2Impl<CompileState, String>(tuple.left(), generate);
        });
    }

    private static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(final CompileState state, final String input) {
        if (Strings.isBlank(input)) {
            return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<Tuple2<CompileState, Whitespace>>();
    }

    private static Option<Tuple2<CompileState, String>> compileFieldDefinition(final CompileState state, final String input) {
        return Main.compileSuffix(Strings.strip(input), ";", (final String withoutEnd) -> Main.getTupleOption(state, withoutEnd).or(() -> Main.compileEnumValues(state, withoutEnd)));
    }

    private static Option<Tuple2<CompileState, String>> getTupleOption(final CompileState state, final String withoutEnd) {
        return Main.parseParameter(state, withoutEnd).flatMap((final Tuple2<CompileState, Parameter> definitionTuple) -> {
            final String generate = "\n\t" + definitionTuple.right().generate(definitionTuple.left().platform()) + ";";
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(definitionTuple.left(), generate));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileEnumValues(final CompileState state, final String withoutEnd) {
        return Main.parseValues(state, withoutEnd, (final CompileState state1, final String s) -> Main.parseInvokable(state1, s).flatMap((final Tuple2<CompileState, Value> tuple) -> {
            final String structureName = state.findLastStructureName().orElse("");
            return tuple.right().generateAsEnumValue(structureName, state.platform()).map((final String stringOption) -> new Tuple2Impl<CompileState, String>(tuple.left(), stringOption));
        })).map((final Tuple2<CompileState, List<String>> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse("")));
    }

    private static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(final CompileState state, final String input) {
        return Main.parseParameter(state, input).orElseGet(() -> new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
    }

    private static Option<Tuple2<CompileState, Parameter>> parseParameter(final CompileState state, final String input) {
        return Main.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> Main.getCompileStateParameterTuple2(tuple))
                .or(() -> Main.parseDefinition(state, input).map((Tuple2<CompileState, Definition> tuple) -> new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
    }

    private static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(final Tuple2<CompileState, Whitespace> tuple) {
        return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinition(final CompileState state, final String input) {
        return Main.compileLast(Strings.strip(input), " ", (final String beforeName, final String name) -> Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main::foldTypeSeparators), (final String beforeType, final String type) -> Main.compileLast(Strings.strip(beforeType), "\n", (final String annotationsString, final String afterAnnotations) -> {
            final List<String> annotations = Main.parseAnnotations(annotationsString);
            return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
        }).or(() -> Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name))).or(() -> Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)));
    }

    private static List<String> parseAnnotations(final String s) {
        return Main.divide(s, (DivideState state1, Character c) -> Main.foldDelimited(state1, c, '\n')).query()
                .map((String s2) -> Strings.strip(s2))
                .filter((String value) -> !Strings.isEmpty(value))
                .filter((String value) -> 1 <= Strings.length(value))
                .map((String value) -> Strings.sliceFrom(value, 1))
                .map((String s1) -> Strings.strip(s1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(final CompileState state, final List<String> annotations, final String beforeType, final String type, final String name) {
        return Main.compileSuffix(Strings.strip(beforeType), ">", (final String withoutTypeParamEnd) -> Main.compileFirst(withoutTypeParamEnd, "<", (final String beforeTypeParams, final String typeParamsString) -> {
            final List<String> typeParams = Main.divideValues(typeParamsString);
            return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
        })).or(() -> {
            final List<String> divided = Main.parseModifiers(beforeType);
            return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
        });
    }

    private static List<String> parseModifiers(final String beforeType) {
        return Main.divide(Strings.strip(beforeType), (DivideState state1, Character c) -> Main.foldDelimited(state1, c, ' '))
                .query()
                .map((String s) -> Strings.strip(s))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static DivideState foldDelimited(final DivideState state1, final char c, final char delimiter) {
        if (delimiter == c) {
            return state1.advance();
        }
        return state1.append(c);
    }

    private static List<String> divideValues(final String input) {
        return Main.divide(input, Main::foldValues)
                .query()
                .map((String input1) -> Strings.strip(input1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    private static DivideState foldTypeSeparators(final DivideState state, final char c) {
        if (' ' == c && state.isLevel()) {
            return state.advance();
        }

        final DivideState appended = state.append(c);
        if ('<' == c) {
            return appended.enter();
        }
        if ('>' == c) {
            return appended.exit();
        }
        return appended;
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(
            final CompileState state,
            final List<String> annotations,
            final List<String> typeParams,
            final List<String> oldModifiers,
            final String type,
            final String name
    ) {
        return Main.parseType(state, type).flatMap((final Tuple2<CompileState, Type> typeTuple) -> {
            final List<String> newModifiers = Main.modifyModifiers(oldModifiers, state.platform());
            final Definition generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
            return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
        });
    }

    private static List<String> modifyModifiers(final List<String> oldModifiers, final Platform platform) {
        final List<String> list = Main.retainFinal(oldModifiers, platform);

        if (oldModifiers.contains("static", Strings::equalsTo)) {
            return list.addLast("static");
        }

        return list;
    }

    private static List<String> retainFinal(final List<String> oldModifiers, final Platform platform) {
        if (oldModifiers.contains("final", Strings::equalsTo) || Platform.Magma != platform) {
            return Lists.empty();
        }

        return Lists.of("mut");
    }

    private static Tuple2<CompileState, Type> parseTypeOrPlaceholder(final CompileState state, final String type) {
        return Main.parseType(state, type)
                .map((Tuple2<CompileState, Type> tuple) -> new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right()))
                .orElseGet(() -> new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
    }

    private static Option<Tuple2<CompileState, String>> compileType(final CompileState state, final String type) {
        return Main.parseType(state, type).map((Tuple2<CompileState, Type> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate()));
    }

    private static Option<Tuple2<CompileState, Type>> parseType(final CompileState state, final String type) {
        return Main.or(state, type, Lists.of(
                Main::parseArrayType,
                Main::parseVarArgs,
                Main::parseGeneric,
                Main::parsePrimitive,
                Main::parseSymbolType
        ));
    }

    private static Option<Tuple2<CompileState, Type>> parseArrayType(final CompileState state, final String input) {
        final String stripped = Strings.strip(input);
        return Main.compileSuffix(stripped, "[]", (final String s) -> {
            final Tuple2<CompileState, Type> child = Main.parseTypeOrPlaceholder(state, s);
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new ArrayType(child.right())));
        });
    }

    private static Option<Tuple2<CompileState, Type>> parseVarArgs(final CompileState state, final String input) {
        final String stripped = Strings.strip(input);
        return Main.compileSuffix(stripped, "...", (final String s) -> {
            final Tuple2<CompileState, Type> child = Main.parseTypeOrPlaceholder(state, s);
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())));
        });
    }

    private static Option<Tuple2<CompileState, Type>> parseSymbolType(final CompileState state, final String input) {
        final String stripped = Strings.strip(input);
        if (Main.isSymbol(stripped)) {
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new SymbolNode(stripped)));
        }
        return new None<Tuple2<CompileState, Type>>();
    }

    private static Option<Tuple2<CompileState, Type>> parsePrimitive(final CompileState state, final String input) {
        return Main.findPrimitiveValue(Strings.strip(input), state.platform()).map((Type result) -> new Tuple2Impl<CompileState, Type>(state, result));
    }

    private static Option<Type> findPrimitiveValue(final String input, final Platform platform) {
        final String stripped = Strings.strip(input);
        if (Strings.equalsTo("char", stripped) || Strings.equalsTo("Character", stripped)) {
            if (Platform.TypeScript == platform) {
                return new Some<Type>(PrimitiveType.String);
            }
            else {
                return new Some<Type>(PrimitiveType.I8);
            }
        }

        if (Strings.equalsTo("String", stripped)) {
            if (Platform.TypeScript == platform) {
                return new Some<Type>(PrimitiveType.String);
            }
            else {
                return new Some<Type>(new SliceType(PrimitiveType.I8));
            }
        }

        if (Strings.equalsTo("int", stripped) || Strings.equalsTo("Integer", stripped)) {
            if (Platform.Magma == platform) {
                return new Some<Type>(PrimitiveType.I32);
            }
            else {
                return new Some<Type>(PrimitiveType.Number);
            }
        }

        if (Strings.equalsTo("boolean", stripped) || Strings.equalsTo("Boolean", stripped)) {
            return new Some<Type>(new BooleanType(platform));
        }

        if (Strings.equalsTo("var", stripped)) {
            return new Some<Type>(PrimitiveType.Var);
        }

        if (Strings.equalsTo("void", stripped)) {
            return new Some<Type>(PrimitiveType.Void);
        }

        return new None<Type>();
    }

    private static Option<Tuple2<CompileState, Type>> parseGeneric(final CompileState state, final String input) {
        return Main.compileSuffix(Strings.strip(input), ">", (final String withoutEnd) -> Main.compileFirst(withoutEnd, "<", (final String baseString, final String argsString) -> {
            final Tuple2<CompileState, List<String>> argsTuple = Main.parseValuesOrEmpty(state, argsString, (CompileState state1, String s) -> Main.compileTypeArgument(state1, s));
            final CompileState argsState = argsTuple.left();
            final List<String> args = argsTuple.right();

            final String base = Strings.strip(baseString);
            return Main.assembleFunctionType(argsState, base, args).or(() -> {
                final CompileState compileState = argsState.addResolvedImportFromCache(base);
                return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)));
            });
        }));
    }

    private static Option<Tuple2<CompileState, Type>> assembleFunctionType(final CompileState state, final String base, final List<String> args) {
        return Main.mapFunctionType(base, args).map((Type generated) -> new Tuple2Impl<CompileState, Type>(state, generated));
    }

    private static Option<Type> mapFunctionType(final String base, final List<String> args) {
        if (Strings.equalsTo("Function", base)) {
            return args.findFirst().and(() -> args.find(1))
                    .map((Tuple2<String, String> tuple) -> new FunctionType(Lists.of(tuple.left()), tuple.right()));
        }

        if (Strings.equalsTo("BiFunction", base)) {
            return args.find(0)
                    .and(() -> args.find(1))
                    .and(() -> args.find(2))
                    .map((Tuple2<Tuple2<String, String>, String> tuple) -> new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right()));
        }

        if (Strings.equalsTo("Supplier", base)) {
            return args.findFirst().map((final String first) -> new FunctionType(Lists.empty(), first));
        }

        if (Strings.equalsTo("Consumer", base)) {
            return args.findFirst().map((final String first) -> new FunctionType(Lists.of(first), "void"));
        }

        if (Strings.equalsTo("Predicate", base)) {
            return args.findFirst().map((final String first) -> new FunctionType(Lists.of(first), "boolean"));
        }

        return new None<Type>();
    }

    private static Option<Tuple2<CompileState, String>> compileTypeArgument(final CompileState state, final String input) {
        return Main.or(state, input, Lists.of(
                (CompileState state2, String input1) -> Main.compileWhitespace(state2, input1),
                (CompileState state1, String type) -> Main.compileType(state1, type)
        ));
    }

    private static <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            final CompileState state,
            final String input,
            final BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseValues(final CompileState state, final String input, final BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper) {
        return Main.parseAll(state, input, Main::foldValues, mapper);
    }

    private static DivideState foldValues(final DivideState state, final char c) {
        if (',' == c && state.isLevel()) {
            return state.advance();
        }

        final DivideState appended = state.append(c);
        if ('-' == c) {
            final char peeked = appended.peek();
            if ('>' == peeked) {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }

        if ('<' == c || '(' == c) {
            return appended.enter();
        }

        if ('>' == c || ')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static <T> Option<T> compileLast(final String input, final String infix, final BiFunction<String, String, Option<T>> mapper) {
        return Main.compileInfix(input, infix, Main::findLast, mapper);
    }

    private static int findLast(final String input, final String infix) {
        return input.lastIndexOf(infix);
    }

    private static <T> Option<T> compileSuffix(final String input, final String suffix, final Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<T>();
        }

        final int length = Strings.length(input);
        final int length1 = Strings.length(suffix);
        final String content = Strings.sliceBetween(input, 0, length - length1);
        return mapper.apply(content);
    }

    private static <T> Option<T> compileFirst(final String input, final String infix, final BiFunction<String, String, Option<T>> mapper) {
        return Main.compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Option<T> compileInfix(final String input, final String infix, final BiFunction<String, String, Integer> locator, final BiFunction<String, String, Option<T>> mapper) {
        return Main.compileSplit(Main.split(input, infix, locator), mapper);
    }

    private static <T> Option<T> compileSplit(final Option<Tuple2<String, String>> splitter, final BiFunction<String, String, Option<T>> mapper) {
        return splitter.flatMap((Tuple2<String, String> tuple) -> mapper.apply(tuple.left(), tuple.right()));
    }

    private static Option<Tuple2<String, String>> split(final String input, final String infix, final BiFunction<String, String, Integer> locator) {
        final Integer index = locator.apply(input, infix);
        if (0 > index) {
            return new None<Tuple2<String, String>>();
        }

        final String left = Strings.sliceBetween(input, 0, index);

        final int length = Strings.length(infix);
        final String right = Strings.sliceFrom(input, index + length);
        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(left, right));
    }

    private static int findFirst(final String input, final String infix) {
        return input.indexOf(infix);
    }

    public static String generatePlaceholder(final String input) {
        final String replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }
}