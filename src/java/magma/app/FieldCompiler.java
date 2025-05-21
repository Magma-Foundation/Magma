package magma.app;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.Stack;
import magma.app.compile.compose.PrefixComposable;
import magma.app.compile.compose.SuffixComposable;
import magma.app.compile.define.ConstructorHeader;
import magma.app.compile.define.Definition;
import magma.app.compile.define.MethodHeader;
import magma.app.compile.define.Parameter;
import magma.app.compile.value.Value;
import magma.app.compile.locate.FirstLocator;
import magma.app.compile.split.LocatingSplitter;

final class FieldCompiler {
    public static Option<Tuple2<CompileState, String>> compileMethod(CompileState state, String input) {
        return CompilerUtils.compileSplit(input, new LocatingSplitter("(", new FirstLocator()), (String beforeParams, String withParams) -> {
            var strippedBeforeParams = Strings.strip(beforeParams);
            return CompilerUtils.compileLast(strippedBeforeParams, " ", (String _, String name) -> {
                if (state.stack().isWithinLast(name)) {
                    return FieldCompiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> {
                if (state.stack().findLastStructureName().filter((String anObject) -> {
                    return Strings.equalsTo(strippedBeforeParams, anObject);
                }).isPresent()) {
                    return FieldCompiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return new None<Tuple2<CompileState, String>>();
            }).or(() -> {
                return DefiningCompiler.parseDefinition(state, beforeParams)
                        .flatMap((Tuple2<CompileState, Definition> tuple) -> {
                            return FieldCompiler.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams);
                        });
            });
        });
    }

    private static Option<Tuple2<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return CompilerUtils.compileSplit(withParams, new LocatingSplitter(")", new FirstLocator()), (String params, String afterParams) -> {
            var parametersTuple = DefiningCompiler.parseParameters(state, params);

            var parametersState = parametersTuple.left();
            var parameters = parametersTuple.right();
            var definitions = DefiningCompiler.retainDefinitionsFromParameters(parameters);

            var joinedDefinitions = definitions.iter()
                    .map((Definition definition) -> {
                        return definition.generate();
                    })
                    .collect(new Joiner(", "))
                    .orElse("");

            if (header.hasAnnotation("Actual")) {
                var headerGenerated = header
                        .removeModifier("static")
                        .generateWithAfterName("(" + joinedDefinitions + ")");

                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, "\n\t" + headerGenerated + ";\n"));
            }

            var headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")");
            return new PrefixComposable<Tuple2<CompileState, String>>("{", (String withoutContentStart) -> {
                return new SuffixComposable<Tuple2<CompileState, String>>("}", (String withoutContentEnd) -> {
                            CompileState compileState = parametersState.enterDepth().enterDepth();
                            var statementsTuple = FunctionSegmentCompiler.compileFunctionStatements(compileState.mapStack((Stack stack1) -> {
                                return stack1.defineAll(definitions);
                            }), withoutContentEnd);

                            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(statementsTuple.left().exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right() + "\n\t}"));
                        }).apply(Strings.strip(withoutContentStart));
            }).apply(Strings.strip(afterParams)).or(() -> {
                if (Strings.equalsTo(";", Strings.strip(afterParams))) {
                    return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(parametersState, "\n\t" + headerGenerated + ";"));
                }

                return new None<Tuple2<CompileState, String>>();
            });
        });
    }

    public static Option<Tuple2<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return new SuffixComposable<Tuple2<CompileState, String>>(";", (String withoutEnd) -> {
            return FieldCompiler.getTupleOption(state, withoutEnd).or(() -> {
                return FieldCompiler.compileEnumValues(state, withoutEnd);
            });
        }).apply(Strings.strip(input));
    }

    private static Option<Tuple2<CompileState, String>> getTupleOption(CompileState state, String withoutEnd) {
        return DefiningCompiler.parseParameter(state, withoutEnd).flatMap((Tuple2<CompileState, Parameter> definitionTuple) -> {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";"));
        });
    }

    public static Option<Tuple2<CompileState, String>> compileEnumValues(CompileState state, String withoutEnd) {
        return CompilerUtils.parseValues(state, withoutEnd, (CompileState state1, String segment) -> {
            var stripped = segment.strip();
            if (ValueCompiler.isSymbol(stripped)) {
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state1, "\n\t static " + stripped + " = \"" + stripped + "\";"));
            }

            return FieldCompiler.getTuple2Option(state, state1, segment);
        }).map((Tuple2<CompileState, List<String>> tuple) -> {
            return new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().iter().collect(new Joiner("")).orElse(""));
        });
    }

    private static Option<Tuple2<CompileState, String>> getTuple2Option(CompileState state, CompileState state1, String segment) {
        return ValueCompiler.parseInvokable(state1, segment).flatMap((Tuple2<CompileState, Value> tuple) -> {
            var structureName = state.stack().findLastStructureName().orElse("");
            return tuple.right().generateAsEnumValue(structureName).map((String stringOption) -> {
                return new Tuple2Impl<CompileState, String>(tuple.left(), stringOption);
            });
        });
    }
}