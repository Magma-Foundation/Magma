package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.Context;
import magma.app.compile.Registry;
import magma.app.compile.Stack;
import magma.app.compile.compose.SuffixComposable;
import magma.app.compile.define.Definition;
import magma.app.compile.locate.FirstLocator;
import magma.app.compile.rule.OrRule;
import magma.app.compile.rule.Rule;
import magma.app.compile.split.LocatingSplitter;
import magma.app.compile.type.Type;
import magma.app.compile.value.Value;

public final class RootCompiler {
    private static Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        return CompilerUtils.compileOrPlaceholder(state, input, Lists.of(
                CompilerUtils::compileWhitespace,
                RootCompiler::compileNamespaced,
                RootCompiler.createStructureRule("class ", "class "),
                RootCompiler.createStructureRule("interface ", "interface "),
                RootCompiler.createStructureRule("record ", "class "),
                RootCompiler.createStructureRule("enum ", "class ")
        ));
    }

    private static Rule<String> createStructureRule(String sourceInfix, String targetInfix) {
        return (CompileState state, String input1) -> {
            return CompilerUtils.compileSplit(input1, new LocatingSplitter(sourceInfix, new FirstLocator()), (String beforeInfix, String afterInfix) -> {
                return CompilerUtils.compileSplit(afterInfix, new LocatingSplitter("{", new FirstLocator()), (String beforeContent, String withEnd) -> {
                    return new SuffixComposable<Tuple2<CompileState, String>>("}", (String inputContent) -> {
                        return CompilerUtils.compileLast(beforeInfix, "\n", (String s, String s2) -> {
                            var annotations = DefiningCompiler.parseAnnotations(s);
                            if (annotations.contains("Actual")) {
                                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
                            }

                            return RootCompiler.compileStructureWithImplementing(state, annotations, DefiningCompiler.parseModifiers(s2), targetInfix, beforeContent, inputContent);
                        }).or(() -> {
                            var modifiers = DefiningCompiler.parseModifiers(beforeContent);
                            return RootCompiler.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
                        });
                    }).apply(Strings.strip(withEnd));
                });
            });
        };
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithImplementing(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, String content) {
        return CompilerUtils.compileLast(beforeContent, " implements ", (String s, String s2) -> {
            return TypeCompiler.parseType(state, s2).flatMap((Tuple2<CompileState, Type> implementingTuple) -> {
                return RootCompiler.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content);
            });
        }).or(() -> {
            return RootCompiler.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content);
        });
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithExtends(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, Option<Type> maybeImplementing, String inputContent) {
        return CompilerUtils.compileSplit(beforeContent, new LocatingSplitter(" extends ", new FirstLocator()), (String beforeExtends, String afterExtends) -> {
            return CompilerUtils.parseValues(state, afterExtends, (CompileState inner0, String inner1) -> {
                        return TypeCompiler.parseType(inner0, inner1);
                    })
                    .flatMap((Tuple2<CompileState, List<Type>> compileStateListTuple2) -> {
                        return RootCompiler.compileStructureWithParameters(compileStateListTuple2.left(), annotations, modifiers, targetInfix, beforeExtends, compileStateListTuple2.right(), maybeImplementing, inputContent);
                    });
        }).or(() -> {
            return RootCompiler.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, Lists.empty(), maybeImplementing, inputContent);
        });
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithParameters(CompileState state, List<String> annotations, List<String> modifiers, String targetInfix, String beforeContent, Iterable<Type> maybeSuperType, Option<Type> maybeImplementing, String inputContent) {
        return CompilerUtils.compileSplit(beforeContent, new LocatingSplitter("(", new FirstLocator()), (String rawName, String withParameters) -> {
            return CompilerUtils.compileSplit(withParameters, new LocatingSplitter(")", new FirstLocator()), (String parametersString, String _) -> {
                var name = Strings.strip(rawName);

                var parametersTuple = DefiningCompiler.parseParameters(state, parametersString);
                var parameters = DefiningCompiler.retainDefinitionsFromParameters(parametersTuple.right());

                return RootCompiler.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
            });
        }).or(() -> {
            return RootCompiler.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType);
        });
    }

    private static Option<Tuple2<CompileState, String>> compileStructureWithTypeParams(CompileState state, String infix, String content, String beforeParams, Iterable<Definition> parameters, Option<Type> maybeImplementing, List<String> annotations, List<String> modifiers, Iterable<Type> maybeSuperType) {
        return new SuffixComposable<Tuple2<CompileState, String>>(">", (String withoutTypeParamEnd) -> {
            return CompilerUtils.compileSplit(withoutTypeParamEnd, new LocatingSplitter("<", new FirstLocator()), (String name, String typeParamsString) -> {
                var typeParams = DefiningCompiler.divideValues(typeParamsString);
                return RootCompiler.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
            });
        }).apply(Strings.strip(beforeParams)).or(() -> {
            return RootCompiler.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType);
        });
    }

    private static Option<Tuple2<CompileState, String>> assembleStructure(
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
        if (!ValueCompiler.isSymbol(name)) {
            return new None<Tuple2<CompileState, String>>();
        }

        var outputContentTuple = CompilerUtils.compileStatements(state.mapStack((Stack stack) -> {
            return stack.pushStructureName(name);
        }), content, RootCompiler::compileClassSegment);
        var outputContentState = outputContentTuple.left().mapStack((Stack stack1) -> {
            return stack1.popStructureName();
        });
        var outputContent = outputContentTuple.right();

        var constructorString = RootCompiler.generateConstructorFromRecordParameters(parameters);
        var joinedTypeParams = RootCompiler.joinTypeParams(typeParams);
        var implementingString = RootCompiler.generateImplementing(maybeImplementing);
        var newModifiers = RootCompiler.modifyModifiers0(oldModifiers);

        var joinedModifiers = newModifiers.iter()
                .map((String value) -> {
                    return value + " ";
                })
                .collect(Joiner.empty())
                .orElse("");

        if (outputContentState.context().hasPlatform(Platform.PlantUML)) {
            var joinedImplementing = maybeImplementing
                    .map((Type type) -> {
                        return type.generateSimple();
                    })
                    .map((String generated) -> {
                        return name + " <|.. " + generated + "\n";
                    })
                    .orElse("");

            var joinedSuperTypes = maybeSuperType.iter()
                    .map((Type type) -> {
                        return type.generateSimple();
                    })
                    .map((String generated) -> {
                        return name + " <|-- " + generated + "\n";
                    })
                    .collect(new Joiner(""))
                    .orElse("");

            var generated = infix + name + joinedTypeParams + " {\n}\n" + joinedSuperTypes + joinedImplementing;
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(outputContentState.mapRegistry((Registry registry) -> {
                return registry.append(generated);
            }), ""));
        }

        if (annotations.contains("Namespace")) {
            String actualInfix = "interface ";
            String newName = name + "Instance";

            var generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + DefiningCompiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
            CompileState compileState = outputContentState.mapRegistry((Registry registry) -> {
                return registry.append(generated);
            });
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(compileState.mapRegistry((Registry registry1) -> {
                return registry1.append("export declare const " + name + ": " + newName + ";\n");
            }), ""));
        }
        else {
            var extendsString = RootCompiler.joinExtends(maybeSuperType);
            var generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + DefiningCompiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(outputContentState.mapRegistry((Registry registry) -> {
                return registry.append(generated);
            }), ""));
        }
    }

    private static String joinExtends(Iterable<Type> maybeSuperType) {
        return maybeSuperType.iter()
                .map((Type type) -> {
                    return type.generate();
                })
                .collect(new Joiner(", "))
                .map((String inner) -> {
                    return " extends " + inner;
                })
                .orElse("");
    }

    private static Iterable<String> modifyModifiers0(List<String> oldModifiers) {
        if (oldModifiers.contains("public")) {
            return Lists.of("export");
        }
        return Lists.empty();
    }

    private static String generateImplementing(Option<Type> maybeImplementing) {
        return maybeImplementing.map((Type type) -> {
                    return type.generate();
                })
                .map((String inner) -> {
                    return " implements " + inner;
                })
                .orElse("");
    }

    public static String joinTypeParams(Iterable<String> typeParams) {
        return typeParams.iter()
                .collect(new Joiner(", "))
                .map((String inner) -> {
                    return "<" + inner + ">";
                })
                .orElse("");
    }

    private static String generateConstructorFromRecordParameters(Iterable<Definition> parameters) {
        return parameters.iter()
                .map((Definition definition) -> {
                    return definition.generate();
                })
                .collect(new Joiner(", "))
                .map((String generatedParameters) -> {
                    return RootCompiler.generateConstructorWithParameterString(parameters, generatedParameters);
                })
                .orElse("");
    }

    private static String generateConstructorWithParameterString(Iterable<Definition> parameters, String parametersString) {
        var constructorAssignments = RootCompiler.generateConstructorAssignments(parameters);

        return "\n\tconstructor (" + parametersString + ") {" +
                constructorAssignments +
                "\n\t}";
    }

    private static String generateConstructorAssignments(Iterable<Definition> parameters) {
        return parameters.iter()
                .map((Definition definition) -> {
                    return definition.toAssignment();
                })
                .collect(Joiner.empty())
                .orElse("");
    }

    private static Option<Tuple2<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ""));
        }

        return new None<Tuple2<CompileState, String>>();
    }

    private static Tuple2<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return CompilerUtils.compileOrPlaceholder(state1, input1, Lists.of(
                CompilerUtils::compileWhitespace,
                RootCompiler.createStructureRule("class ", "class "),
                RootCompiler.createStructureRule("interface ", "interface "),
                RootCompiler.createStructureRule("record ", "class "),
                RootCompiler.createStructureRule("enum ", "class "),
                FieldCompiler::compileMethod,
                FieldCompiler::compileFieldDefinition,
                FieldCompiler::compileEnumValues
        ));
    }

    public static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, String input) {
        return new OrRule<Value>(Lists.of(
                ValueCompiler::parseLambda,
                ValueCompiler.createOperatorRule("+"),
                ValueCompiler.createOperatorRule("-"),
                ValueCompiler.createOperatorRule("<="),
                ValueCompiler.createOperatorRule("<"),
                ValueCompiler.createOperatorRule("&&"),
                ValueCompiler.createOperatorRule("||"),
                ValueCompiler.createOperatorRule(">"),
                ValueCompiler.createOperatorRule(">="),
                ValueCompiler::parseInvokable,
                ValueCompiler.createAccessRule("."),
                ValueCompiler.createAccessRule("::"),
                ValueCompiler::parseSymbol,
                ValueCompiler::parseNot,
                ValueCompiler::parseNumber,
                ValueCompiler.createOperatorRuleWithDifferentInfix("==", "==="),
                ValueCompiler.createOperatorRuleWithDifferentInfix("!=", "!=="),
                ValueCompiler.createTextRule("\""),
                ValueCompiler.createTextRule("'")
        )).apply(state, input);
    }

    public static Tuple2<CompileState, String> compileRoot(CompileState state, String input, Location location) {
        return CompilerUtils.compileStatements(state.mapContext((Context context2) -> {
            return context2.withLocation(location);
        }), input, RootCompiler::compileRootSegment);
    }
}