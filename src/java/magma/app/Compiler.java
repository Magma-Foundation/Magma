package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iter;
import magma.api.collect.Iters;
import magma.api.collect.Joiner;
import magma.api.collect.head.HeadedIter;
import magma.api.collect.head.RangeHead;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Characters;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.Dependency;
import magma.app.compile.DivideState;
import magma.app.compile.ImmutableDivideState;
import magma.app.compile.Import;
import magma.app.compile.Registry;
import magma.app.compile.define.ConstructionCaller;
import magma.app.compile.define.ConstructorHeader;
import magma.app.compile.define.Definition;
import magma.app.compile.define.MethodHeader;
import magma.app.compile.define.Parameter;
import magma.app.compile.text.Placeholder;
import magma.app.compile.text.Symbol;
import magma.app.compile.text.Whitespace;
import magma.app.compile.type.FunctionType;
import magma.app.compile.type.PrimitiveType;
import magma.app.compile.type.TemplateType;
import magma.app.compile.type.Type;
import magma.app.compile.type.VariadicType;
import magma.app.compile.value.AccessValue;
import magma.app.compile.value.Argument;
import magma.app.compile.value.Caller;
import magma.app.compile.value.Invokable;
import magma.app.compile.value.Lambda;
import magma.app.compile.value.Not;
import magma.app.compile.value.Operation;
import magma.app.compile.value.StringValue;
import magma.app.compile.value.Value;
import magma.app.io.Source;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Compiler {
    public static Tuple2<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return Compiler.compileAll(state, input, Compiler::foldStatements, mapper, Compiler::mergeStatements);
    }

    public static Tuple2<CompileState, String> compileAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, BiFunction<String, String, String> merger) {
        var folded = Compiler.parseAll(state, input, folder, (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s));
        }).orElse(new Tuple2Impl<CompileState, List<String>>(state, Lists.empty()));
        return new Tuple2Impl<CompileState, String>(folded.left(), Compiler.generateAll(folded.right(), merger));
    }

    public static String generateAll(Iterable<String> elements, BiFunction<String, String, String> merger) {
        return elements.iter()
                .foldWithInitial("", merger);
    }

    public static <T> Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> biFunction) {
        return Compiler.divide(input, folder).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (Option<Tuple2<CompileState, List<T>>> maybeCurrent, String segment) -> maybeCurrent.flatMap((Tuple2<CompileState, List<T>> current) -> {
            var currentState = current.left();
            var currentElement = current.right();

            return biFunction.apply(currentState, segment).map((Tuple2<CompileState, T> mappedTuple) -> {
                var mappedState = mappedTuple.left();
                var mappedElement = mappedTuple.right();
                return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
            });
        }));
    }

    public static String mergeStatements(String cache, String element) {
        return cache + element;
    }

    public static Iter<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = Compiler.createInitialDivideState(input);

        while (true) {
            var poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, Character>(current, '\0'));
            if (!poppedTuple0.left()) {
                break;
            }

            var poppedTuple = poppedTuple0.right();
            var poppedState = poppedTuple.left();
            var popped = poppedTuple.right();

            current = Compiler.foldSingleQuotes(poppedState, popped)
                    .or(() -> Compiler.foldDoubleQuotes(poppedState, popped))
                    .orElseGet(() -> folder.apply(poppedState, popped));
        }

        return current.advance().query();
    }

    public static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if ('\"' != c) {
            return new None<DivideState>();
        }

        var appended = state.append(c);
        while (true) {
            var maybeTuple = appended.popAndAppendToTuple()
                    .toTuple(new Tuple2Impl<DivideState, Character>(appended, '\0'));

            if (!maybeTuple.left()) {
                break;
            }

            var tuple = maybeTuple.right();
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

    public static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if ('\'' != c) {
            return new None<DivideState>();
        }

        return state.append(c)
                .popAndAppendToTuple()
                .flatMap(Compiler::foldEscaped)
                .flatMap((DivideState state1) -> state1.popAndAppendToOption());
    }

    public static Option<DivideState> foldEscaped(Tuple2<DivideState, Character> tuple) {
        var state = tuple.left();
        var c = tuple.right();

        if ('\\' == c) {
            return state.popAndAppendToOption();
        }

        return new Some<DivideState>(state);
    }

    public static DivideState foldStatements(DivideState state, char c) {
        var appended = state.append(c);
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

    public static Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        return Compiler.compileOrPlaceholder(state, input, Lists.of(
                Compiler::compileWhitespace,
                Compiler::compileNamespaced,
                Compiler.createStructureRule("class ", "class "),
                Compiler.createStructureRule("interface ", "interface "),
                Compiler.createStructureRule("record ", "class "),
                Compiler.createStructureRule("enum ", "class ")
        ));
    }

    public static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureRule(String sourceInfix, String targetInfix) {
        return (CompileState state, String input1) -> Compiler.compileFirst(input1, sourceInfix, (String beforeInfix, String afterInfix) -> Compiler.compileFirst(afterInfix, "{", (String beforeContent, String withEnd) -> Compiler.compileSuffix(Strings.strip(withEnd), "}", (String inputContent) -> Compiler.compileLast(beforeInfix, "\n", (String s, String s2) -> {
            var annotations = Compiler.parseAnnotations(s);
            if (annotations.contains("Actual")) {
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
            }

            return Compiler.compileStructureWithImplementing(state, annotations, Compiler.parseModifiers(s2), targetInfix, beforeContent, inputContent);
        }).or(() -> {
            var modifiers = Compiler.parseModifiers(beforeContent);
            return Compiler.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
        }))));
    }

    public static Option<Tuple2<CompileState, String>> compileStructureWithImplementing(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, String content) {
        return Compiler.compileLast(beforeContent, " implements ", (String s, String s2) -> Compiler.parseType(state, s2).flatMap((Tuple2<CompileState, Type> implementingTuple) -> Compiler.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content))).or(() -> Compiler.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content));
    }

    public static Option<Tuple2<CompileState, String>> compileStructureWithExtends(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, Option<Type> maybeImplementing, String inputContent) {
        return Compiler.compileFirst(beforeContent, " extends ", (String beforeExtends, String afterExtends) -> Compiler.parseValues(state, afterExtends, (CompileState inner0, String inner1) -> Compiler.parseType(inner0, inner1))
                .flatMap((Tuple2<CompileState, List<Type>> compileStateListTuple2) -> Compiler.compileStructureWithParameters(compileStateListTuple2.left(), annotations, modifiers, targetInfix, beforeExtends, compileStateListTuple2.right(), maybeImplementing, inputContent))).or(() -> Compiler.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, Lists.empty(), maybeImplementing, inputContent));
    }

    public static Option<Tuple2<CompileState, String>> compileStructureWithParameters(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, Iterable<Type> maybeSuperType, Option<Type> maybeImplementing, String inputContent) {
        return Compiler.compileFirst(beforeContent, "(", (String rawName, String withParameters) -> Compiler.compileFirst(withParameters, ")", (String parametersString, String _) -> {
            var name = Strings.strip(rawName);

            var parametersTuple = Compiler.parseParameters(state, parametersString);
            var parameters = Compiler.retainDefinitionsFromParameters(parametersTuple.right());

            return Compiler.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
        })).or(() -> Compiler.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType));
    }

    public static Iterable<Definition> retainDefinitionsFromParameters(Iterable<Parameter> parameters) {
        return parameters.iter()
                .map((Parameter parameter) -> parameter.asDefinition())
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<Definition>());
    }

    public static Option<Tuple2<CompileState, String>> compileStructureWithTypeParams(CompileState state, String infix, String content, String beforeParams, Iterable<Definition> parameters, Option<Type> maybeImplementing, List<String> annotations, List<String> modifiers, Iterable<Type> maybeSuperType) {
        return Compiler.compileSuffix(Strings.strip(beforeParams), ">", (String withoutTypeParamEnd) -> Compiler.compileFirst(withoutTypeParamEnd, "<", (String name, String typeParamsString) -> {
            var typeParams = Compiler.divideValues(typeParamsString);
            return Compiler.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
        })).or(() -> Compiler.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType));
    }

    public static Option<Tuple2<CompileState, String>> assembleStructure(
            CompileState state,
            List<String> annotations,
            List<String> oldModifiers,
            String infix,
            String rawName,
            Iterable<String> typeParams,
            Iterable<Definition> parameters,
            Option<Type> maybeImplementing,
            String content,
            Iterable<Type> maybeSuperType
    ) {
        var name = Strings.strip(rawName);
        if (!Compiler.isSymbol(name)) {
            return new None<Tuple2<CompileState, String>>();
        }

        var outputContentTuple = Compiler.compileStatements(state.pushStructureName(name), content, Compiler::compileClassSegment);
        var outputContentState = outputContentTuple.left().popStructureName();
        var outputContent = outputContentTuple.right();

        var constructorString = Compiler.generateConstructorFromRecordParameters(parameters);
        var joinedTypeParams = Compiler.joinTypeParams(typeParams);
        var implementingString = Compiler.generateImplementing(maybeImplementing);
        var newModifiers = Compiler.modifyModifiers0(oldModifiers);

        var joinedModifiers = newModifiers.iter()
                .map((String value) -> value + " ")
                .collect(Joiner.empty())
                .orElse("");

        if (outputContentState.context().hasPlatform(Platform.PlantUML)) {
            var joinedImplementing = maybeImplementing
                    .map((Type type) -> type.generateSimple())
                    .map((String generated) -> name + " <|.. " + generated + "\n")
                    .orElse("");

            var joinedSuperTypes = maybeSuperType.iter()
                    .map((Type type) -> type.generateSimple())
                    .map((String generated) -> name + " <|-- " + generated + "\n")
                    .collect(new Joiner(""))
                    .orElse("");

            var generated = infix + name + joinedTypeParams + " {\n}\n" + joinedSuperTypes + joinedImplementing;
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(outputContentState.append(generated), ""));
        }

        if (annotations.contains("Namespace")) {
            String actualInfix = "interface ";
            String newName = name + "Instance";

            var generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Compiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(outputContentState.append(generated)
                    .append("export declare const " + name + ": " + newName + ";\n"), ""));
        }
        else {
            var extendsString = Compiler.joinExtends(maybeSuperType);
            var generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + Compiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(outputContentState.append(generated), ""));
        }
    }

    public static String joinExtends(Iterable<Type> maybeSuperType) {
        return maybeSuperType.iter()
                .map((Type type) -> type.generate())
                .collect(new Joiner(", "))
                .map((String inner) -> " extends " + inner)
                .orElse("");
    }

    public static Iterable<String> modifyModifiers0(List<String> oldModifiers) {
        if (oldModifiers.contains("public")) {
            return Lists.of("export");
        }
        return Lists.empty();
    }

    public static String generateImplementing(Option<Type> maybeImplementing) {
        return maybeImplementing.map((Type type) -> type.generate())
                .map((String inner) -> " implements " + inner)
                .orElse("");
    }

    public static String joinTypeParams(Iterable<String> typeParams) {
        return typeParams.iter()
                .collect(new Joiner(", "))
                .map((String inner) -> "<" + inner + ">")
                .orElse("");
    }

    public static String generateConstructorFromRecordParameters(Iterable<Definition> parameters) {
        return parameters.iter()
                .map((Definition definition) -> definition.generate())
                .collect(new Joiner(", "))
                .map((String generatedParameters) -> Compiler.generateConstructorWithParameterString(parameters, generatedParameters))
                .orElse("");
    }

    public static String generateConstructorWithParameterString(Iterable<Definition> parameters, String parametersString) {
        var constructorAssignments = Compiler.generateConstructorAssignments(parameters);

        return "\n\tconstructor (" + parametersString + ") {" +
                constructorAssignments +
                "\n\t}";
    }

    public static String generateConstructorAssignments(Iterable<Definition> parameters) {
        return parameters.iter()
                .map((Definition definition) -> definition.toAssignment())
                .collect(Joiner.empty())
                .orElse("");
    }

    public static String joinParameters(Iterable<Definition> parameters) {
        return parameters.iter()
                .map((Definition definition) -> definition.generate())
                .map((String generated) -> "\n\t" + generated + ";")
                .collect(Joiner.empty())
                .orElse("");
    }

    public static Option<Tuple2<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
        }

        return new None<Tuple2<CompileState, String>>();
    }

    public static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            Iterable<BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>>> rules
    ) {
        return Compiler.or(state, input, rules).orElseGet(() -> new Tuple2Impl<CompileState, String>(state, Compiler.generatePlaceholder(input)));
    }

    public static <T> Option<Tuple2<CompileState, T>> or(
            CompileState state,
            String input,
            Iterable<BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>> rules
    ) {
        return rules.iter()
                .map((BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) -> Compiler.getApply(state, input, rule))
                .flatMap(Iters::fromOption)
                .next();
    }

    public static <T> Option<Tuple2<CompileState, T>> getApply(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) {
        return rule.apply(state, input);
    }

    public static Tuple2<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return Compiler.compileOrPlaceholder(state1, input1, Lists.of(
                Compiler::compileWhitespace,
                Compiler.createStructureRule("class ", "class "),
                Compiler.createStructureRule("interface ", "interface "),
                Compiler.createStructureRule("record ", "class "),
                Compiler.createStructureRule("enum ", "class "),
                Compiler::compileMethod,
                Compiler::compileFieldDefinition,
                Compiler::compileEnumValues
        ));
    }

    public static Option<Tuple2<CompileState, String>> compileMethod(CompileState state, String input) {
        return Compiler.compileFirst(input, "(", (String beforeParams, String withParams) -> {
            var strippedBeforeParams = Strings.strip(beforeParams);
            return Compiler.compileLast(strippedBeforeParams, " ", (String _, String name) -> {
                if (state.stack().isWithinLast(name)) {
                    return Compiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> {
                if (state.stack().findLastStructureName().filter((String anObject) -> Strings.equalsTo(strippedBeforeParams, anObject)).isPresent()) {
                    return Compiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> Compiler.parseDefinition(state, beforeParams)
                    .flatMap((Tuple2<CompileState, Definition> tuple) -> Compiler.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)));
        });
    }

    public static Option<Tuple2<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return Compiler.compileFirst(withParams, ")", (String params, String afterParams) -> {
            var parametersTuple = Compiler.parseParameters(state, params);

            var parametersState = parametersTuple.left();
            var parameters = parametersTuple.right();
            var definitions = Compiler.retainDefinitionsFromParameters(parameters);

            var joinedDefinitions = definitions.iter()
                    .map((Definition definition) -> definition.generate())
                    .collect(new Joiner(", "))
                    .orElse("");

            if (header.hasAnnotation("Actual")) {
                var headerGenerated = header
                        .removeModifier("static")
                        .generateWithAfterName("(" + joinedDefinitions + ")");

                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, "\n\t" + headerGenerated + ";\n"));
            }

            var headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")");
            return Compiler.compilePrefix(Strings.strip(afterParams), "{", (String withoutContentStart) -> Compiler.compileSuffix(Strings.strip(withoutContentStart), "}", (String withoutContentEnd) -> {
                var statementsTuple = Compiler.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);

                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(statementsTuple.left().exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right() + "\n\t}"));
            })).or(() -> {
                if (Strings.equalsTo(";", Strings.strip(afterParams))) {
                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, "\n\t" + headerGenerated + ";"));
                }

                return new None<Tuple2<CompileState, String>>();
            });
        });
    }

    public static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return Compiler.parseValuesOrEmpty(state, params, (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, Parameter>>(Compiler.parseParameterOrPlaceholder(state1, s));
        });
    }

    public static Tuple2<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return Compiler.compileStatements(state, input, Compiler::compileFunctionSegment);
    }

    public static Tuple2<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return Compiler.compileOrPlaceholder(state, input, Lists.of(
                Compiler::compileWhitespace,
                Compiler::compileEmptySegment,
                Compiler::compileBlock,
                Compiler::compileFunctionStatement,
                Compiler::compileReturnWithoutSuffix
        ));
    }

    public static Option<Tuple2<CompileState, String>> compileEmptySegment(CompileState state, String input) {
        if (Strings.equalsTo(";", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ";"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    public static Option<Tuple2<CompileState, String>> compileBlock(CompileState state, String input) {
        return Compiler.compileSuffix(Strings.strip(input), "}", (String withoutEnd) -> Compiler.compileSplit(Compiler.splitFoldedLast(withoutEnd, "", Compiler::foldBlockStarts), (String beforeContentWithEnd, String content) -> Compiler.compileSuffix(beforeContentWithEnd, "{", (String beforeContent) -> Compiler.compileBlockHeader(state, beforeContent).flatMap((Tuple2<CompileState, String> headerTuple) -> {
            var contentTuple = Compiler.compileFunctionStatements(headerTuple.left().enterDepth(), content);

            var indent = state.createIndent();
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
        }))));
    }

    public static DivideState foldBlockStarts(DivideState state, char c) {
        var appended = state.append(c);
        if ('{' == c) {
            var entered = appended.enter();
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

    public static Option<Tuple2<CompileState, String>> compileBlockHeader(CompileState state, String input) {
        return Compiler.or(state, input, Lists.of(
                Compiler.createConditionalRule("if"),
                Compiler.createConditionalRule("while"),
                Compiler::compileElse
        ));
    }

    public static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createConditionalRule(String prefix) {
        return (CompileState state1, String input1) -> Compiler.compilePrefix(Strings.strip(input1), prefix, (String withoutPrefix) -> {
            var strippedCondition = Strings.strip(withoutPrefix);
            return Compiler.compilePrefix(strippedCondition, "(", (String withoutConditionStart) -> Compiler.compileSuffix(withoutConditionStart, ")", (String withoutConditionEnd) -> {
                var tuple = Compiler.compileValueOrPlaceholder(state1, withoutConditionEnd);
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), prefix + " (" + tuple.right() + ")"));
            }));
        });
    }

    public static Option<Tuple2<CompileState, String>> compileElse(CompileState state, String input) {
        if (Strings.equalsTo("else", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "else "));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    public static Option<Tuple2<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return Compiler.compileSuffix(Strings.strip(input), ";", (String withoutEnd) -> {
            var valueTuple = Compiler.compileFunctionStatementValue(state, withoutEnd);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
        });
    }

    public static Tuple2<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return Compiler.compileOrPlaceholder(state, withoutEnd, Lists.of(
                Compiler::compileReturnWithValue,
                Compiler::compileAssignment,
                (CompileState state1, String input) -> Compiler.parseInvokable(state1, input)
                        .map((Tuple2<CompileState, Value> tuple) -> Compiler.generateValue(tuple)),
                Compiler.createPostRule("++"),
                Compiler.createPostRule("--"),
                Compiler::compileBreak
        ));
    }

    public static Tuple2Impl<CompileState, String> generateValue(Tuple2<CompileState, Value> tuple) {
        var state = tuple.left();
        var right = tuple.right();
        var generated = right.generate();
        var s = Compiler.generatePlaceholder(right.resolve(state).generate());
        return new Tuple2Impl<CompileState, String>(state, generated + s);
    }

    public static Option<Tuple2<CompileState, String>> compileBreak(CompileState state, String input) {
        if (Strings.equalsTo("break", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "break"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    public static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createPostRule(String suffix) {
        return (CompileState state1, String input) -> Compiler.compileSuffix(Strings.strip(input), suffix, (String child) -> {
            var tuple = Compiler.compileValueOrPlaceholder(state1, child);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right() + suffix));
        });
    }

    public static Option<Tuple2<CompileState, String>> compileReturnWithValue(CompileState state, String input) {
        return Compiler.compileReturn(input, (String value1) -> Compiler.compileValue(state, value1));
    }

    public static Option<Tuple2<CompileState, String>> compileReturn(String input, Function<String, Option<Tuple2<CompileState, String>>> mapper) {
        return Compiler.compilePrefix(Strings.strip(input), "return ", (String value) -> mapper.apply(value).flatMap((Tuple2<CompileState, String> tuple) -> new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), "return " + tuple.right()))));
    }

    public static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, String input) {
        return Compiler.compileSuffix(Strings.strip(input), ")", (String withoutEnd) -> Compiler.compileSplit(Compiler.splitFoldedLast(withoutEnd, "", Compiler::foldInvocationStarts), (String callerWithArgStart, String args) -> Compiler.compileSuffix(callerWithArgStart, "(", (String callerString) -> Compiler.compilePrefix(Strings.strip(callerString), "new ", (String type) -> Compiler.compileType(state, type).flatMap((Tuple2<CompileState, String> callerTuple) -> {
            var callerState = callerTuple.right();
            var caller = callerTuple.left();
            return Compiler.assembleInvokable(caller, new ConstructionCaller(callerState), args);
        })).or(() -> Compiler.parseValue(state, callerString).flatMap((Tuple2<CompileState, Value> callerTuple) -> Compiler.assembleInvokable(callerTuple.left(), callerTuple.right(), args))))));
    }

    public static Option<Tuple2<String, String>> splitFoldedLast(String input, String delimiter, BiFunction<DivideState, Character, DivideState> folder) {
        return Compiler.splitFolded(input, folder, (List<String> divisions1) -> Compiler.selectLast(divisions1, delimiter));
    }

    public static Option<Tuple2<String, String>> splitFolded(
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            Function<List<String>, Option<Tuple2<String, String>>> selector
    ) {
        var divisions = Compiler.divide(input, folder)
                .collect(new ListCollector<String>());

        if (2 > divisions.size()) {
            return new None<Tuple2<String, String>>();
        }

        return selector.apply(divisions);
    }

    public static Option<Tuple2<String, String>> selectLast(List<String> divisions, String delimiter) {
        var beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
        var last = divisions.findLast().orElse("");

        var joined = beforeLast.iter()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(joined, last));
    }

    public static DivideState foldInvocationStarts(DivideState state, char c) {
        var appended = state.append(c);
        if ('(' == c) {
            var entered = appended.enter();
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

    public static Option<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, String argsString) {
        return Compiler.parseValues(state, argsString, (CompileState state1, String s) -> {
            return Compiler.parseArgument(state1, s);
        }).flatMap((Tuple2<CompileState, List<Argument>> argsTuple) -> {
            var argsState = argsTuple.left();
            var args = Compiler.retain(argsTuple.right(), (Argument argument) -> argument.toValue());

            var newCaller = Compiler.transformCaller(argsState, oldCaller);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)));
        });
    }

    public static Caller transformCaller(CompileState state, Caller oldCaller) {
        return oldCaller.findChild().flatMap((Value parent) -> {
            var parentType = parent.resolve(state);
            if (parentType.isFunctional()) {
                return new Some<Caller>(parent);
            }

            return new None<Caller>();
        }).orElse(oldCaller);
    }

    public static <T, R> Iterable<R> retain(Iterable<T> args, Function<T, Option<R>> mapper) {
        return args.iter()
                .map(mapper)
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<R>());
    }

    public static Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state1, String input) {
        return Compiler.parseValue(state1, input)
                .map((Tuple2<CompileState, Value> tuple) -> new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
    }

    public static Option<Tuple2<CompileState, String>> compileAssignment(CompileState state, String input) {
        return Compiler.compileFirst(input, "=", (String destination, String source) -> {
            var sourceTuple = Compiler.compileValueOrPlaceholder(state, source);

            var destinationTuple = Compiler.compileValue(sourceTuple.left(), destination)
                    .or(() -> Compiler.parseDefinition(sourceTuple.left(), destination).map((Tuple2<CompileState, Definition> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), "let " + tuple.right().generate())))
                    .orElseGet(() -> new Tuple2Impl<CompileState, String>(sourceTuple.left(), Compiler.generatePlaceholder(destination)));

            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
        });
    }

    public static Tuple2<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return Compiler.compileValue(state, input).orElseGet(() -> new Tuple2Impl<CompileState, String>(state, Compiler.generatePlaceholder(input)));
    }

    public static Option<Tuple2<CompileState, String>> compileValue(CompileState state, String input) {
        return Compiler.parseValue(state, input).map((Tuple2<CompileState, Value> tuple) -> Compiler.generateValue(tuple));
    }

    public static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, String input) {
        return Compiler.or(state, input, Lists.of(
                Compiler::parseLambda,
                Compiler.createOperatorRule("+"),
                Compiler.createOperatorRule("-"),
                Compiler.createOperatorRule("<="),
                Compiler.createOperatorRule("<"),
                Compiler.createOperatorRule("&&"),
                Compiler.createOperatorRule("||"),
                Compiler.createOperatorRule(">"),
                Compiler.createOperatorRule(">="),
                Compiler::parseInvokable,
                Compiler.createAccessRule("."),
                Compiler.createAccessRule("::"),
                Compiler::parseSymbol,
                Compiler::parseNot,
                Compiler::parseNumber,
                Compiler.createOperatorRuleWithDifferentInfix("==", "==="),
                Compiler.createOperatorRuleWithDifferentInfix("!=", "!=="),
                Compiler.createTextRule("\""),
                Compiler.createTextRule("'")
        ));
    }

    public static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createTextRule(String slice) {
        return (CompileState state1, String input1) -> {
            var stripped = Strings.strip(input1);
            return Compiler.compilePrefix(stripped, slice, (String s) -> Compiler.compileSuffix(s, slice, (String s1) -> new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1)))));
        };
    }

    public static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, String input) {
        return Compiler.compilePrefix(Strings.strip(input), "!", (String withoutPrefix) -> {
            var childTuple = Compiler.compileValueOrPlaceholder(state, withoutPrefix);
            var childState = childTuple.left();
            var child = "!" + childTuple.right();
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)));
        });
    }

    public static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, String input) {
        return Compiler.compileFirst(input, "->", (String beforeArrow, String afterArrow) -> {
            var strippedBeforeArrow = Strings.strip(beforeArrow);
            return Compiler.compilePrefix(strippedBeforeArrow, "(", (String withoutStart) -> {
                return Compiler.compileSuffix(withoutStart, ")", (String withoutEnd) -> {
                    return Compiler.parseValues(state, withoutEnd, (CompileState state1, String s) -> {
                        return Compiler.parseParameter(state1, s);
                    }).flatMap((Tuple2<CompileState, List<Parameter>> paramNames) -> {
                        return Compiler.compileLambdaWithParameterNames(paramNames.left(), Compiler.retainDefinitionsFromParameters(paramNames.right()), afterArrow);
                    });
                });
            });
        });
    }

    public static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, Iterable<Definition> paramNames, String afterArrow) {
        var strippedAfterArrow = Strings.strip(afterArrow);
        return Compiler.compilePrefix(strippedAfterArrow, "{", (String withoutContentStart) -> Compiler.compileSuffix(withoutContentStart, "}", (String withoutContentEnd) -> {
            var statementsTuple = Compiler.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
            var statementsState = statementsTuple.left();
            var statements = statementsTuple.right();

            var exited = statementsState.exitDepth();
            return Compiler.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}");
        })).or(() -> Compiler.compileValue(state, strippedAfterArrow).flatMap((Tuple2<CompileState, String> tuple) -> Compiler.assembleLambda(tuple.left(), paramNames, tuple.right())));
    }

    public static Option<Tuple2<CompileState, Value>> assembleLambda(CompileState exited, Iterable<Definition> paramNames, String content) {
        return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)));
    }

    public static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createOperatorRule(String infix) {
        return Compiler.createOperatorRuleWithDifferentInfix(infix, infix);
    }

    public static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createAccessRule(String infix) {
        return (CompileState state, String input) -> Compiler.compileLast(input, infix, (String childString, String rawProperty) -> {
            var property = Strings.strip(rawProperty);
            if (!Compiler.isSymbol(property)) {
                return new None<Tuple2<CompileState, Value>>();
            }

            return Compiler.parseValue(state, childString).flatMap((Tuple2<CompileState, Value> childTuple) -> {
                var childState = childTuple.left();
                var child = childTuple.right();
                return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessValue(child, property)));
            });
        });
    }

    public static BiFunction<CompileState, String, Option<Tuple2<CompileState, Value>>> createOperatorRuleWithDifferentInfix(String sourceInfix, String targetInfix) {
        return (CompileState state1, String input1) -> Compiler.compileSplit(Compiler.splitFolded(input1, Compiler.foldOperator(sourceInfix), (List<String> divisions) -> Compiler.selectFirst(divisions, sourceInfix)), (String leftString, String rightString) -> Compiler.parseValue(state1, leftString).flatMap((Tuple2<CompileState, Value> leftTuple) -> Compiler.parseValue(leftTuple.left(), rightString).flatMap((Tuple2<CompileState, Value> rightTuple) -> {
            var left = leftTuple.right();
            var right = rightTuple.right();
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)));
        })));
    }

    public static Option<Tuple2<String, String>> selectFirst(List<String> divisions, String delimiter) {
        var first = divisions.findFirst().orElse("");
        var afterFirst = divisions.subList(1, divisions.size()).orElse(divisions)
                .iter()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(first, afterFirst));
    }

    public static BiFunction<DivideState, Character, DivideState> foldOperator(String infix) {
        return (DivideState state, Character c) -> {
            if (c == infix.charAt(0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
                var length = Strings.length(infix) - 1;
                var counter = 0;
                var current = state;
                while (counter < length) {
                    counter++;

                    current = current.pop().map((Tuple2<DivideState, Character> tuple) -> tuple.left()).orElse(current);
                }
                return current.advance();
            }

            return state.append(c);
        };
    }

    public static Option<Tuple2<CompileState, Value>> parseNumber(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (Compiler.isNumber(stripped)) {
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new Symbol(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    public static boolean isNumber(String input) {
        var query = new HeadedIter<Integer>(new RangeHead(Strings.length(input)));
        return query.map(input::charAt).allMatch((Character c) -> Characters.isDigit(c));
    }

    public static Option<Tuple2<CompileState, Value>> parseSymbol(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (Compiler.isSymbol(stripped)) {
            var withImport = Compiler.addResolvedImportFromCache0(state, stripped);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new Symbol(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    public static boolean isSymbol(String input) {
        var query = new HeadedIter<Integer>(new RangeHead(Strings.length(input)));
        return query.allMatch((Integer index) -> Compiler.isSymbolChar(index, input.charAt(index)));
    }

    public static boolean isSymbolChar(int index, char c) {
        return '_' == c
                || Characters.isLetter(c)
                || (0 != index && Characters.isDigit(c));
    }

    public static <T> Option<Tuple2<CompileState, T>> compilePrefix(
            String input,
            String infix,
            Function<String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        if (!input.startsWith(infix)) {
            return new None<Tuple2<CompileState, T>>();
        }

        var slice = Strings.sliceFrom(input, Strings.length(infix));
        return mapper.apply(slice);
    }

    public static Option<Tuple2<CompileState, String>> compileWhitespace(CompileState state, String input) {
        return Compiler.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate()));
    }

    public static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (Strings.isBlank(input)) {
            return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<Tuple2<CompileState, Whitespace>>();
    }

    public static Option<Tuple2<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return Compiler.compileSuffix(Strings.strip(input), ";", (String withoutEnd) -> Compiler.getTupleOption(state, withoutEnd).or(() -> Compiler.compileEnumValues(state, withoutEnd)));
    }

    public static Option<Tuple2<CompileState, String>> getTupleOption(CompileState state, String withoutEnd) {
        return Compiler.parseParameter(state, withoutEnd).flatMap((Tuple2<CompileState, Parameter> definitionTuple) -> new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";")));
    }

    public static Option<Tuple2<CompileState, String>> compileEnumValues(CompileState state, String withoutEnd) {
        return Compiler.parseValues(state, withoutEnd, (CompileState state1, String segment) -> {
            var stripped = segment.strip();
            if (Compiler.isSymbol(stripped)) {
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state1, "\n\t static " + stripped + " = \"" + stripped + "\";"));
            }

            return Compiler.getTuple2Option(state, state1, segment);
        }).map((Tuple2<CompileState, List<String>> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().iter().collect(new Joiner("")).orElse("")));
    }

    public static Option<Tuple2<CompileState, String>> getTuple2Option(CompileState state, CompileState state1, String segment) {
        return Compiler.parseInvokable(state1, segment).flatMap((Tuple2<CompileState, Value> tuple) -> {
            var structureName = state.stack().findLastStructureName().orElse("");
            return tuple.right().generateAsEnumValue(structureName).map((String stringOption) -> new Tuple2Impl<CompileState, String>(tuple.left(), stringOption));
        });
    }

    public static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, String input) {
        return Compiler.parseParameter(state, input).orElseGet(() -> new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
    }

    public static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, String input) {
        return Compiler.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> Compiler.getCompileStateParameterTuple2(tuple))
                .or(() -> Compiler.parseDefinition(state, input).map((Tuple2<CompileState, Definition> tuple) -> new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
    }

    public static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
        return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
    }

    public static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return Compiler.compileLast(Strings.strip(input), " ", (String beforeName, String name) -> Compiler.compileSplit(Compiler.splitFoldedLast(Strings.strip(beforeName), " ", Compiler::foldTypeSeparators), (String beforeType, String type) -> Compiler.compileLast(Strings.strip(beforeType), "\n", (String annotationsString, String afterAnnotations) -> {
            var annotations = Compiler.parseAnnotations(annotationsString);
            return Compiler.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
        }).or(() -> Compiler.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name))).or(() -> Compiler.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)));
    }

    public static List<String> parseAnnotations(String s) {
        return Compiler.divide(s, (DivideState state1, Character c) -> Compiler.foldDelimited(state1, c, '\n'))
                .map((String s2) -> Strings.strip(s2))
                .filter((String value) -> !Strings.isEmpty(value))
                .filter((String value) -> 1 <= Strings.length(value))
                .map((String value) -> Strings.sliceFrom(value, 1))
                .map((String s1) -> Strings.strip(s1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    public static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(
            CompileState state,
            List<String> annotations,
            String beforeType,
            String type,
            String name
    ) {
        return Compiler.compileSuffix(Strings.strip(beforeType), ">", (String withoutTypeParamEnd) -> Compiler.compileFirst(withoutTypeParamEnd, "<", (String beforeTypeParams, String typeParamsString) -> {
            var typeParams = Compiler.divideValues(typeParamsString);
            return Compiler.parseDefinitionWithTypeParameters(state, annotations, typeParams, Compiler.parseModifiers(beforeTypeParams), type, name);
        })).or(() -> {
            var divided = Compiler.parseModifiers(beforeType);
            return Compiler.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
        });
    }

    public static List<String> parseModifiers(String beforeType) {
        return Compiler.divide(Strings.strip(beforeType), (DivideState state1, Character c) -> Compiler.foldDelimited(state1, c, ' '))
                .map((String s) -> Strings.strip(s))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    public static DivideState foldDelimited(DivideState state1, char c, char delimiter) {
        if (delimiter == c) {
            return state1.advance();
        }
        return state1.append(c);
    }

    public static List<String> divideValues(String input) {
        return Compiler.divide(input, Compiler::foldValues)
                .map((String input1) -> Strings.strip(input1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    public static DivideState foldTypeSeparators(DivideState state, char c) {
        if (' ' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('<' == c) {
            return appended.enter();
        }
        if ('>' == c) {
            return appended.exit();
        }
        return appended;
    }

    public static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(
            CompileState state,
            List<String> annotations,
            List<String> typeParams,
            List<String> oldModifiers,
            String type,
            String name
    ) {
        return Compiler.parseType(state, type).flatMap((Tuple2<CompileState, Type> typeTuple) -> {
            var newModifiers = Compiler.modifyModifiers(oldModifiers);
            var generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
            return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
        });
    }

    public static List<String> modifyModifiers(List<String> oldModifiers) {
        if (oldModifiers.contains("static")) {
            return Lists.of("static");
        }
        return Lists.empty();
    }

    public static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String type) {
        return Compiler.parseType(state, type)
                .map((Tuple2<CompileState, Type> tuple) -> new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right()))
                .orElseGet(() -> new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
    }

    public static Option<Tuple2<CompileState, String>> compileType(CompileState state, String type) {
        return Compiler.parseType(state, type).map((Tuple2<CompileState, Type> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate()));
    }

    public static Option<Tuple2<CompileState, Type>> parseType(CompileState state, String type) {
        return Compiler.or(state, type, Lists.of(
                Compiler::parseVarArgs,
                Compiler::parseGeneric,
                Compiler::parsePrimitive,
                Compiler::parseSymbolType
        ));
    }

    public static Option<Tuple2<CompileState, Type>> parseVarArgs(CompileState state, String input) {
        var stripped = Strings.strip(input);
        return Compiler.compileSuffix(stripped, "...", (String s) -> {
            var child = Compiler.parseTypeOrPlaceholder(state, s);
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())));
        });
    }

    public static Option<Tuple2<CompileState, Type>> parseSymbolType(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (Compiler.isSymbol(stripped)) {
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(Compiler.addResolvedImportFromCache0(state, stripped), new Symbol(stripped)));
        }
        return new None<Tuple2<CompileState, Type>>();
    }

    public static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        return Compiler.findPrimitiveValue(Strings.strip(input)).map((Type result) -> new Tuple2Impl<CompileState, Type>(state, result));
    }

    public static Option<Type> findPrimitiveValue(String input) {
        var stripped = Strings.strip(input);
        if (Strings.equalsTo("char", stripped) || Strings.equalsTo("Character", stripped) || Strings.equalsTo("String", stripped)) {
            return new Some<Type>(PrimitiveType.String);
        }

        if (Strings.equalsTo("int", stripped) || Strings.equalsTo("Integer", stripped)) {
            return new Some<Type>(PrimitiveType.Number);
        }

        if (Strings.equalsTo("boolean", stripped) || Strings.equalsTo("Boolean", stripped)) {
            return new Some<Type>(PrimitiveType.Boolean);
        }

        if (Strings.equalsTo("var", stripped)) {
            return new Some<Type>(PrimitiveType.Var);
        }

        if (Strings.equalsTo("void", stripped)) {
            return new Some<Type>(PrimitiveType.Void);
        }

        return new None<Type>();
    }

    public static Option<Tuple2<CompileState, Type>> parseGeneric(CompileState state, String input) {
        return Compiler.compileSuffix(Strings.strip(input), ">", (String withoutEnd) -> Compiler.compileFirst(withoutEnd, "<", (String baseString, String argsString) -> {
            var argsTuple = Compiler.parseValuesOrEmpty(state, argsString, (CompileState state1, String s) -> Compiler.compileTypeArgument(state1, s));
            var argsState = argsTuple.left();
            var args = argsTuple.right();

            var base = Strings.strip(baseString);
            return Compiler.assembleFunctionType(argsState, base, args).or(() -> {
                var compileState = Compiler.addResolvedImportFromCache0(argsState, base);
                return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)));
            });
        }));
    }

    public static Option<Tuple2<CompileState, Type>> assembleFunctionType(CompileState state, String base, List<String> args) {
        return Compiler.mapFunctionType(base, args).map((Type generated) -> new Tuple2Impl<CompileState, Type>(state, generated));
    }

    public static Option<Type> mapFunctionType(String base, List<String> args) {
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
            return args.findFirst().map((String first) -> new FunctionType(Lists.empty(), first));
        }

        if (Strings.equalsTo("Consumer", base)) {
            return args.findFirst().map((String first) -> new FunctionType(Lists.of(first), "void"));
        }

        if (Strings.equalsTo("Predicate", base)) {
            return args.findFirst().map((String first) -> new FunctionType(Lists.of(first), "boolean"));
        }

        return new None<Type>();
    }

    public static Option<Tuple2<CompileState, String>> compileTypeArgument(CompileState state, String input) {
        return Compiler.or(state, input, Lists.of(
                (CompileState state2, String input1) -> Compiler.compileWhitespace(state2, input1),
                (CompileState state1, String type) -> Compiler.compileType(state1, type)
        ));
    }

    public static String generateValueStrings(Iterable<String> values) {
        return Compiler.generateAll(values, Compiler::mergeValues);
    }

    public static <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        return Compiler.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
    }

    public static <T> Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper) {
        return Compiler.parseAll(state, input, Compiler::foldValues, mapper);
    }

    public static String mergeValues(String cache, String element) {
        if (Strings.isEmpty(cache)) {
            return cache + element;
        }
        return cache + ", " + element;
    }

    public static DivideState foldValues(DivideState state, char c) {
        if (',' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('-' == c) {
            var peeked = appended.peek();
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

    public static <T> Option<T> compileLast(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return Compiler.compileInfix(input, infix, Compiler::findLast, mapper);
    }

    public static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }

    public static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<T>();
        }

        var length = Strings.length(input);
        var length1 = Strings.length(suffix);
        var content = Strings.sliceBetween(input, 0, length - length1);
        return mapper.apply(content);
    }

    public static <T> Option<T> compileFirst(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return Compiler.compileInfix(input, infix, Compiler::findFirst, mapper);
    }

    public static <T> Option<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Option<T>> mapper) {
        return Compiler.compileSplit(Compiler.split(input, infix, locator), mapper);
    }

    public static <T> Option<T> compileSplit(Option<Tuple2<String, String>> splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.flatMap((Tuple2<String, String> tuple) -> mapper.apply(tuple.left(), tuple.right()));
    }

    public static Option<Tuple2<String, String>> split(String input, String infix, BiFunction<String, String, Integer> locator) {
        var index = locator.apply(input, infix);
        if (0 > index) {
            return new None<Tuple2<String, String>>();
        }

        var left = Strings.sliceBetween(input, 0, index);

        var length = Strings.length(infix);
        var right = Strings.sliceFrom(input, index + length);
        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(left, right));
    }

    public static int findFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    public static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }

    public static DivideState createInitialDivideState(String input) {
        return new ImmutableDivideState(Lists.empty(), "", 0, input, 0);
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithoutSuffix(CompileState state1, String input1) {
        return Compiler.compileReturn(input1, (String withoutPrefix) -> Compiler.compileValue(state1, withoutPrefix))
                .map((Tuple2<CompileState, String> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), state1.createIndent() + tuple.right()));
    }

    static Tuple2<CompileState, String> compileRoot(CompileState state, String input, Location location) {
        return Compiler.compileStatements(state.withLocation(location), input, Compiler::compileRootSegment);
    }

    public static List<String> fixNamespace(List<String> requestedNamespace, List<String> thisNamespace) {
        if (thisNamespace.isEmpty()) {
            return requestedNamespace.addFirst(".");
        }

        return Compiler.addParentSeparator(requestedNamespace, thisNamespace.size());
    }

    private static List<String> addParentSeparator(List<String> newNamespace, int count) {
        var index = 0;
        var copy = newNamespace;
        while (index < count) {
            copy = copy.addFirst("..");
            index++;
        }

        return copy;
    }

    public static Option<CompileState> getCompileState1(CompileState immutableCompileState, Location location) {
        if (!immutableCompileState.context().hasPlatform(Platform.PlantUML)) {
            return new None<>();
        }

        var name = immutableCompileState.context().findNameOrEmpty();
        var dependency = new Dependency(name, location.name());
        if (immutableCompileState.registry().containsDependency(dependency)) {
            return new None<>();
        }

        return new Some<>(immutableCompileState.mapRegistry((Registry registry1) -> registry1.addDependency(dependency)));
    }

    public static CompileState getState(CompileState immutableCompileState, Location location) {
        var requestedNamespace = location.namespace();
        var requestedChild = location.name();

        var namespace = Compiler.fixNamespace(requestedNamespace, immutableCompileState.context().findNamespaceOrEmpty());
        if (immutableCompileState.registry().doesImportExistAlready(requestedChild)) {
            return immutableCompileState;
        }

        var namespaceWithChild = namespace.addLast(requestedChild);
        var anImport = new Import(namespaceWithChild, requestedChild);
        return immutableCompileState.mapRegistry((Registry registry) -> registry.addImport(anImport));
    }

    public static CompileState addResolvedImportFromCache0(CompileState state, String base) {
        if (state.stack().hasAnyStructureName(base)) {
            return state;
        }

        return state.context()
                .findSource(base)
                .map((Source source) -> {
                    Location location = source.createLocation();
                    return Compiler.getCompileState1(state, location)
                            .orElseGet(() -> Compiler.getState(state, location));
                })
                .orElse(state);
    }
}