package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.DivideState;
import magma.app.compile.compose.Composable;
import magma.app.compile.compose.PrefixComposable;
import magma.app.compile.compose.SplitComposable;
import magma.app.compile.compose.SuffixComposable;
import magma.app.compile.define.Definition;
import magma.app.compile.fold.StatementsFolder;
import magma.app.compile.locate.FirstLocator;
import magma.app.compile.merge.Merger;
import magma.app.compile.merge.StatementsMerger;
import magma.app.compile.rule.OrRule;
import magma.app.compile.rule.Rule;
import magma.app.compile.select.LastSelector;
import magma.app.compile.select.Selector;
import magma.app.compile.split.FoldingSplitter;
import magma.app.compile.split.LocatingSplitter;
import magma.app.compile.split.Splitter;
import magma.app.compile.value.Placeholder;
import magma.app.compile.value.Value;

import java.util.function.BiFunction;
import java.util.function.Function;

final class FunctionSegmentCompiler {
    private static Option<Tuple2<CompileState, String>> compileEmptySegment(CompileState state, String input) {
        if (Strings.equalsTo(";", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ";"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static Option<Tuple2<CompileState, String>> compileBlock(CompileState state, String input) {
        return new SuffixComposable<Tuple2<CompileState, String>>("}", (String withoutEnd) -> {
            return new SplitComposable<Tuple2<CompileState, String>>((String withoutEnd0) -> {
                    Selector selector = new LastSelector("");
                    return new FoldingSplitter((DivideState state1, char c) -> {
                        return FunctionSegmentCompiler.foldBlockStarts(state1, c);
                    }, selector).apply(withoutEnd0);
                }, Composable.toComposable((String beforeContentWithEnd, String content) -> {
                    return new SuffixComposable<Tuple2<CompileState, String>>("{", (String beforeContent) -> {
                        return FunctionSegmentCompiler.compileBlockHeader(state, beforeContent).flatMap((Tuple2<CompileState, String> headerTuple) -> {
                            var contentTuple = FunctionSegmentCompiler.compileFunctionStatements(headerTuple.left().enterDepth(), content);

                            var indent = state.createIndent();
                            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
                        });
                    }).apply(beforeContentWithEnd);
                })).apply(withoutEnd);
        }).apply(Strings.strip(input));
    }

    private static DivideState foldBlockStarts(DivideState state, char c) {
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

    private static Option<Tuple2<CompileState, String>> compileBlockHeader(CompileState state, String input) {
        return new OrRule<String>(Lists.of(
                FunctionSegmentCompiler.createConditionalRule("if"),
                FunctionSegmentCompiler.createConditionalRule("while"),
                FunctionSegmentCompiler::compileElse
        )).apply(state, input);
    }

    private static Rule<String> createConditionalRule(String prefix) {
        return (CompileState state1, String input1) -> {
            return new PrefixComposable<Tuple2<CompileState, String>>(prefix, (String withoutPrefix) -> {
                var strippedCondition = Strings.strip(withoutPrefix);
                return new PrefixComposable<Tuple2<CompileState, String>>("(", (String withoutConditionStart) -> {
                    return new SuffixComposable<Tuple2<CompileState, String>>(")", (String withoutConditionEnd) -> {
                        var tuple = ValueCompiler.compileValueOrPlaceholder(state1, withoutConditionEnd);
                        return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), prefix + " (" + tuple.right() + ")"));
                    }).apply(withoutConditionStart);
                }).apply(strippedCondition);
            }).apply(Strings.strip(input1));
        };
    }

    private static Option<Tuple2<CompileState, String>> compileElse(CompileState state, String input) {
        if (Strings.equalsTo("else", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "else "));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static Option<Tuple2<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return new SuffixComposable<Tuple2<CompileState, String>>(";", (String withoutEnd) -> {
            var valueTuple = FunctionSegmentCompiler.compileFunctionStatementValue(state, withoutEnd);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
        }).apply(Strings.strip(input));
    }

    private static Tuple2<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return OrRule.compileOrPlaceholder(state, withoutEnd, Lists.of(
                FunctionSegmentCompiler::compileReturnWithValue,
                FunctionSegmentCompiler::compileAssignment,
                FunctionSegmentCompiler.createInvokableRule(),
                FunctionSegmentCompiler.createPostRule("++"),
                FunctionSegmentCompiler.createPostRule("--"),
                FunctionSegmentCompiler::compileBreak
        ));
    }

    private static Rule<String> createInvokableRule() {
        return (CompileState state1, String input) -> {
            return ValueCompiler.parseInvokable(state1, input)
                    .map((Tuple2<CompileState, Value> tuple) -> {
                        return ValueCompiler.generateValue(tuple);
                    });
        };
    }

    private static Option<Tuple2<CompileState, String>> compileBreak(CompileState state, String input) {
        if (Strings.equalsTo("break", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "break"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    private static Rule<String> createPostRule(String suffix) {
        return (CompileState state1, String input) -> {
            return new SuffixComposable<Tuple2<CompileState, String>>(suffix, (String child) -> {
                var tuple = ValueCompiler.compileValueOrPlaceholder(state1, child);
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right() + suffix));
            }).apply(Strings.strip(input));
        };
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithValue(CompileState state, String input) {
        return FunctionSegmentCompiler.compileReturn(input, (String value1) -> {
            return ValueCompiler.compileValue(state, value1);
        });
    }

    private static Option<Tuple2<CompileState, String>> compileReturn(String input, Function<String, Option<Tuple2<CompileState, String>>> mapper) {
        return new PrefixComposable<Tuple2<CompileState, String>>("return ", (String value) -> {
            return mapper.apply(value).flatMap((Tuple2<CompileState, String> tuple) -> {
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), "return " + tuple.right()));
            });
        }).apply(Strings.strip(input));
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithoutSuffix(CompileState state1, String input1) {
        return FunctionSegmentCompiler.compileReturn(input1, (String withoutPrefix) -> {
                    return ValueCompiler.compileValue(state1, withoutPrefix);
                })
                .map((Tuple2<CompileState, String> tuple) -> {
                    return new Tuple2Impl<CompileState, String>(tuple.left(), state1.createIndent() + tuple.right());
                });
    }

    private static Option<Tuple2<CompileState, String>> compileAssignment(CompileState state, String input) {
        Splitter splitter = new LocatingSplitter("=", new FirstLocator());
        return new SplitComposable<Tuple2<CompileState, String>>(splitter, Composable.toComposable((String destination, String source) -> {
            var sourceTuple = ValueCompiler.compileValueOrPlaceholder(state, source);

            var destinationTuple = ValueCompiler.compileValue(sourceTuple.left(), destination)
                    .or(() -> {
                        return DefiningCompiler.parseDefinition(sourceTuple.left(), destination).map((Tuple2<CompileState, Definition> tuple) -> {
                            return new Tuple2Impl<CompileState, String>(tuple.left(), "let " + tuple.right().generate());
                        });
                    })
                    .orElseGet(() -> {
                        return new Tuple2Impl<CompileState, String>(sourceTuple.left(), Placeholder.generatePlaceholder(destination));
                    });

            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
        })).apply(input);
    }

    public static Tuple2<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return FunctionSegmentCompiler.compileStatements(state, input, FunctionSegmentCompiler::compileFunctionSegment);
    }

    private static Tuple2<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return OrRule.compileOrPlaceholder(state, input, Lists.of(
                WhitespaceCompiler::compileWhitespace,
                FunctionSegmentCompiler::compileEmptySegment,
                FunctionSegmentCompiler::compileBlock,
                FunctionSegmentCompiler::compileFunctionStatement,
                FunctionSegmentCompiler::compileReturnWithoutSuffix
        ));
    }

    static Tuple2<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return new DivideRule<>(new StatementsFolder(), DivideRule.toRule(mapper))
                .apply(state, input)
                .map(folded -> Merger.generateAllFromTuple(folded.left(), folded.right(), new StatementsMerger()))
                .orElse(new Tuple2Impl<CompileState, String>(state, ""));
    }
}