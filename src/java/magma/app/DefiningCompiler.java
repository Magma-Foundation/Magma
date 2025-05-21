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
import magma.app.compile.ValueUtils;
import magma.app.compile.compose.Composable;
import magma.app.compile.compose.SplitComposable;
import magma.app.compile.compose.SuffixComposable;
import magma.app.compile.fold.DelimitedFolder;
import magma.app.compile.DivideState;
import magma.app.compile.define.Definition;
import magma.app.compile.define.Parameter;
import magma.app.compile.select.Selector;
import magma.app.compile.split.FoldingSplitter;
import magma.app.compile.split.Splitter;
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
                .map((Parameter parameter) -> {
                    return parameter.asDefinition();
                })
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<Definition>());
    }

    public static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return ValueUtils.parseValuesOrEmpty(state, params, (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, Parameter>>(DefiningCompiler.parseParameterOrPlaceholder(state1, s));
        });
    }

    public static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, String input) {
        return DefiningCompiler.parseParameter(state, input).orElseGet(() -> {
            return new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input));
        });
    }

    public static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, String input) {
        return WhitespaceCompiler.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> {
                    return DefiningCompiler.getCompileStateParameterTuple2(tuple);
                })
                .or(() -> {
                    return DefiningCompiler.parseDefinition(state, input).map((Tuple2<CompileState, Definition> tuple) -> {
                        return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
                    });
                });
    }

    private static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
        return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
    }

    public static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return SplitComposable.compileLast(Strings.strip(input), " ", (String beforeName, String name) -> {
            return new SplitComposable<Tuple2<CompileState, Definition>>((String beforeName0) -> {
                Selector selector = new LastSelector(" ");
                return new FoldingSplitter(new TypeSeparatorFolder(), selector).apply(Strings.strip(beforeName0));
            }, Composable.toComposable((String beforeType, String type) -> {
                return SplitComposable.compileLast(Strings.strip(beforeType), "\n", (String annotationsString, String afterAnnotations) -> {
                    var annotations = DefiningCompiler.parseAnnotations(annotationsString);
                    return DefiningCompiler.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
                }).or(() -> {
                    return DefiningCompiler.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name);
                });
            })).apply(beforeName).or(() -> {
                return DefiningCompiler.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name);
            });
        });
    }

    public static List<String> parseAnnotations(String s) {
        return new FoldedDivider(new DecoratedFolder((DivideState state1, char c) -> {
            return new DelimitedFolder('\n').apply(state1, c);
        })).divide(s)
                .map((String s2) -> {
                    return Strings.strip(s2);
                })
                .filter((String value) -> {
                    return !Strings.isEmpty(value);
                })
                .filter((String value) -> {
                    return 1 <= Strings.length(value);
                })
                .map((String value) -> {
                    return Strings.sliceFrom(value, 1);
                })
                .map((String s1) -> {
                    return Strings.strip(s1);
                })
                .filter((String value) -> {
                    return !Strings.isEmpty(value);
                })
                .collect(new ListCollector<String>());
    }

    private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(
            CompileState state,
            List<String> annotations,
            String beforeType,
            String type,
            String name
    ) {
        return new SuffixComposable<Tuple2<CompileState, Definition>>(">", (String withoutTypeParamEnd) -> {
            Splitter splitter = new LocatingSplitter("<", new FirstLocator());
            return new SplitComposable<Tuple2<CompileState, Definition>>(splitter, Composable.toComposable((String beforeTypeParams, String typeParamsString) -> {
                    var typeParams = DefiningCompiler.divideValues(typeParamsString);
                    return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, typeParams, DefiningCompiler.parseModifiers(beforeTypeParams), type, name);
                })).apply(withoutTypeParamEnd);
        }).apply(Strings.strip(beforeType)).or(() -> {
            var divided = DefiningCompiler.parseModifiers(beforeType);
            return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
        });
    }

    public static List<String> parseModifiers(String beforeType) {
        return new FoldedDivider(new DecoratedFolder((DivideState state1, char c) -> {
            return new DelimitedFolder(' ').apply(state1, c);
        })).divide(Strings.strip(beforeType))
                .map((String s) -> {
                    return Strings.strip(s);
                })
                .filter((String value) -> {
                    return !Strings.isEmpty(value);
                })
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
                .map((Definition definition) -> {
                    return definition.generate();
                })
                .map((String generated) -> {
                    return "\n\t" + generated + ";";
                })
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
                .map((String input1) -> {
                    return Strings.strip(input1);
                })
                .filter((String value) -> {
                    return !Strings.isEmpty(value);
                })
                .collect(new ListCollector<String>());
    }
}