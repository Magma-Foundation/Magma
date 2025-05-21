package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.Dependency;
import magma.app.compile.Import;
import magma.app.compile.Registry;
import magma.app.compile.compose.SuffixComposable;
import magma.app.compile.rule.OrRule;
import magma.app.compile.type.FunctionType;
import magma.app.compile.type.PrimitiveType;
import magma.app.compile.type.TemplateType;
import magma.app.compile.type.Type;
import magma.app.compile.type.VariadicType;
import magma.app.compile.value.Placeholder;
import magma.app.compile.value.Symbol;
import magma.app.io.Source;
import magma.app.compile.locate.FirstLocator;
import magma.app.compile.split.LocatingSplitter;

final class TypeCompiler {
    public static Option<Tuple2<CompileState, String>> compileType(CompileState state, String type) {
        return TypeCompiler.parseType(state, type).map((Tuple2<CompileState, Type> tuple) -> {
            return new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate());
        });
    }

    public static Option<Tuple2<CompileState, Type>> parseType(CompileState state, String type) {
        return new OrRule<Type>(Lists.of(
                TypeCompiler::parseVarArgs,
                TypeCompiler::parseGeneric,
                TypeCompiler::parsePrimitive,
                TypeCompiler::parseSymbolType
        )).apply(state, type);
    }

    private static Option<Tuple2<CompileState, Type>> parseVarArgs(CompileState state, String input) {
        var stripped = Strings.strip(input);
        return new SuffixComposable<>("...", (String s) -> {
            var child = TypeCompiler.parseTypeOrPlaceholder(state, s);
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())));
        }).apply(stripped);
    }

    private static Option<Tuple2<CompileState, Type>> parseSymbolType(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (ValueCompiler.isSymbol(stripped)) {
            return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(TypeCompiler.addResolvedImportFromCache0(state, stripped), new Symbol(stripped)));
        }
        return new None<Tuple2<CompileState, Type>>();
    }

    private static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        return TypeCompiler.findPrimitiveValue(Strings.strip(input)).map((Type result) -> {
            return new Tuple2Impl<CompileState, Type>(state, result);
        });
    }

    private static Option<Type> findPrimitiveValue(String input) {
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

    private static Option<Tuple2<CompileState, Type>> parseGeneric(CompileState state, String input) {
        return new SuffixComposable<>(">", (String withoutEnd) -> {
            return CompilerUtils.compileSplit(withoutEnd, new LocatingSplitter("<", new FirstLocator()), (String baseString, String argsString) -> {
                var argsTuple = CompilerUtils.parseValuesOrEmpty(state, argsString, (CompileState state1, String s) -> {
                    return TypeCompiler.compileTypeArgument(state1, s);
                });
                var argsState = argsTuple.left();
                var args = argsTuple.right();

                var base = Strings.strip(baseString);
                return TypeCompiler.assembleFunctionType(argsState, base, args).or(() -> {
                    var compileState = TypeCompiler.addResolvedImportFromCache0(argsState, base);
                    return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)));
                });
            });
        }).apply(Strings.strip(input));
    }

    private static Option<Tuple2<CompileState, Type>> assembleFunctionType(CompileState state, String base, List<String> args) {
        return TypeCompiler.mapFunctionType(base, args).map((Type generated) -> {
            return new Tuple2Impl<CompileState, Type>(state, generated);
        });
    }

    private static Option<Type> mapFunctionType(String base, List<String> args) {
        if (Strings.equalsTo("Function", base)) {
            return args.findFirst().and(() -> {
                        return args.find(1);
                    })
                    .map((Tuple2<String, String> tuple) -> {
                        return new FunctionType(Lists.of(tuple.left()), tuple.right());
                    });
        }

        if (Strings.equalsTo("BiFunction", base)) {
            return args.find(0)
                    .and(() -> {
                        return args.find(1);
                    })
                    .and(() -> {
                        return args.find(2);
                    })
                    .map((Tuple2<Tuple2<String, String>, String> tuple) -> {
                        return new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right());
                    });
        }

        if (Strings.equalsTo("Supplier", base)) {
            return args.findFirst().map((String first) -> {
                return new FunctionType(Lists.empty(), first);
            });
        }

        if (Strings.equalsTo("Consumer", base)) {
            return args.findFirst().map((String first) -> {
                return new FunctionType(Lists.of(first), "void");
            });
        }

        if (Strings.equalsTo("Predicate", base)) {
            return args.findFirst().map((String first) -> {
                return new FunctionType(Lists.of(first), "boolean");
            });
        }

        return new None<Type>();
    }

    private static Option<Tuple2<CompileState, String>> compileTypeArgument(CompileState state, String input) {
        return new OrRule<String>(Lists.of(
                (CompileState state2, String input1) -> {
                    return CompilerUtils.compileWhitespace(state2, input1);
                },
                (CompileState state1, String type) -> {
                    return TypeCompiler.compileType(state1, type);
                }
        )).apply(state, input);
    }

    private static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String type) {
        return TypeCompiler.parseType(state, type)
                .map((Tuple2<CompileState, Type> tuple) -> {
                    return new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right());
                })
                .orElseGet(() -> {
                    return new Tuple2Impl<CompileState, Type>(state, new Placeholder(type));
                });
    }

    private static CompileState getState(CompileState immutableCompileState, Location location) {
        var requestedNamespace = location.namespace();
        var requestedChild = location.name();

        var namespace = TypeCompiler.fixNamespace(requestedNamespace, immutableCompileState.context().findNamespaceOrEmpty());
        if (immutableCompileState.registry().doesImportExistAlready(requestedChild)) {
            return immutableCompileState;
        }

        var namespaceWithChild = namespace.addLast(requestedChild);
        var anImport = new Import(namespaceWithChild, requestedChild);
        return immutableCompileState.mapRegistry((Registry registry) -> {
            return registry.addImport(anImport);
        });
    }

    public static CompileState addResolvedImportFromCache0(CompileState state, String base) {
        if (state.stack().hasAnyStructureName(base)) {
            return state;
        }

        return state.context()
                .findSource(base)
                .map((Source source) -> {
                    Location location = source.createLocation();
                    return TypeCompiler.getCompileState1(state, location)
                            .orElseGet(() -> {
                                return TypeCompiler.getState(state, location);
                            });
                })
                .orElse(state);
    }

    private static Option<CompileState> getCompileState1(CompileState immutableCompileState, Location location) {
        if (!immutableCompileState.context().hasPlatform(Platform.PlantUML)) {
            return new None<CompileState>();
        }

        var name = immutableCompileState.context().findNameOrEmpty();
        var dependency = new Dependency(name, location.name());
        if (immutableCompileState.registry().containsDependency(dependency)) {
            return new None<CompileState>();
        }

        return new Some<CompileState>(immutableCompileState.mapRegistry((Registry registry1) -> {
            return registry1.addDependency(dependency);
        }));
    }

    private static List<String> fixNamespace(List<String> requestedNamespace, List<String> thisNamespace) {
        if (thisNamespace.isEmpty()) {
            return requestedNamespace.addFirst(".");
        }

        return TypeCompiler.addParentSeparator(requestedNamespace, thisNamespace.size());
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
}
