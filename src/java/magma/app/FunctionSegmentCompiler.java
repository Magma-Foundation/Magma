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
import magma.app.compile.define.Definition;
import magma.app.compile.rule.OrRule;
import magma.app.compile.rule.Rule;
import magma.app.compile.select.Selector;
import magma.app.compile.split.FoldingSplitter;
import magma.app.compile.value.Value;
import magma.app.compile.locate.FirstLocator;
import magma.app.compile.select.LastSelector;
import magma.app.compile.split.LocatingSplitter;

import java.util.function.BiFunction;
import java.util.function.Function;

final class FunctionSegmentCompiler {
    public static Option<Tuple2<CompileState, String>> compileEmptySegment(CompileState state, String input) {
        if (Strings.equalsTo(";", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, ";"));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    public static Option<Tuple2<CompileState, String>> compileBlock(CompileState state, String input) {
        return CompilerUtils.compileSuffix(Strings.strip(input), "}", (String withoutEnd) -> {
            return CompilerUtils.compileSplit(withoutEnd, (withoutEnd0) -> {
                Selector selector = new LastSelector("");
                return new FoldingSplitter((state1, c) -> {
                            return foldBlockStarts(state1, c);
                        }, selector).apply(withoutEnd0);
            }, (String beforeContentWithEnd, String content) -> CompilerUtils.compileSuffix(beforeContentWithEnd, "{", (String beforeContent) -> FunctionSegmentCompiler.compileBlockHeader(state, beforeContent).flatMap((Tuple2<CompileState, String> headerTuple) -> {
                var contentTuple = FunctionSegmentCompiler.compileFunctionStatements(headerTuple.left().enterDepth(), content);

                var indent = state.createIndent();
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
            })));
        });
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
        return CompilerUtils.or(state, input, new OrRule<>(Lists.of(
                FunctionSegmentCompiler.createConditionalRule("if"),
                FunctionSegmentCompiler.createConditionalRule("while"),
                FunctionSegmentCompiler::compileElse
        )));
    }

    private static Rule<String> createConditionalRule(String prefix) {
        return (CompileState state1, String input1) -> CompilerUtils.compilePrefix(Strings.strip(input1), prefix, (String withoutPrefix) -> {
            var strippedCondition = Strings.strip(withoutPrefix);
            return CompilerUtils.compilePrefix(strippedCondition, "(", (String withoutConditionStart) -> CompilerUtils.compileSuffix(withoutConditionStart, ")", (String withoutConditionEnd) -> {
                var tuple = ValueCompiler.compileValueOrPlaceholder(state1, withoutConditionEnd);
                return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), prefix + " (" + tuple.right() + ")"));
            }));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileElse(CompileState state, String input) {
        if (Strings.equalsTo("else", Strings.strip(input))) {
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(state, "else "));
        }
        else {
            return new None<Tuple2<CompileState, String>>();
        }
    }

    public static Option<Tuple2<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return CompilerUtils.compileSuffix(Strings.strip(input), ";", (String withoutEnd) -> {
            var valueTuple = FunctionSegmentCompiler.compileFunctionStatementValue(state, withoutEnd);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
        });
    }

    private static Tuple2<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return CompilerUtils.compileOrPlaceholder(state, withoutEnd, Lists.of(
                FunctionSegmentCompiler::compileReturnWithValue,
                FunctionSegmentCompiler::compileAssignment,
                (CompileState state1, String input) -> ValueCompiler.parseInvokable(state1, input)
                        .map((Tuple2<CompileState, Value> tuple) -> ValueCompiler.generateValue(tuple)),
                FunctionSegmentCompiler.createPostRule("++"),
                FunctionSegmentCompiler.createPostRule("--"),
                FunctionSegmentCompiler::compileBreak
        ));
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
        return (CompileState state1, String input) -> CompilerUtils.compileSuffix(Strings.strip(input), suffix, (String child) -> {
            var tuple = ValueCompiler.compileValueOrPlaceholder(state1, child);
            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right() + suffix));
        });
    }

    private static Option<Tuple2<CompileState, String>> compileReturnWithValue(CompileState state, String input) {
        return FunctionSegmentCompiler.compileReturn(input, (String value1) -> ValueCompiler.compileValue(state, value1));
    }

    private static Option<Tuple2<CompileState, String>> compileReturn(String input, Function<String, Option<Tuple2<CompileState, String>>> mapper) {
        return CompilerUtils.compilePrefix(Strings.strip(input), "return ", (String value) -> mapper.apply(value).flatMap((Tuple2<CompileState, String> tuple) -> new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(tuple.left(), "return " + tuple.right()))));
    }

    public static Option<Tuple2<CompileState, String>> compileReturnWithoutSuffix(CompileState state1, String input1) {
        return FunctionSegmentCompiler.compileReturn(input1, (String withoutPrefix) -> ValueCompiler.compileValue(state1, withoutPrefix))
                .map((Tuple2<CompileState, String> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), state1.createIndent() + tuple.right()));
    }

    private static Option<Tuple2<CompileState, String>> compileAssignment(CompileState state, String input) {
        return CompilerUtils.compileSplit(input, new LocatingSplitter("=", new FirstLocator()), (String destination, String source) -> {
            var sourceTuple = ValueCompiler.compileValueOrPlaceholder(state, source);

            var destinationTuple = ValueCompiler.compileValue(sourceTuple.left(), destination)
                    .or(() -> DefiningCompiler.parseDefinition(sourceTuple.left(), destination).map((Tuple2<CompileState, Definition> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), "let " + tuple.right().generate())))
                    .orElseGet(() -> new Tuple2Impl<CompileState, String>(sourceTuple.left(), CompilerUtils.generatePlaceholder(destination)));

            return new Some<Tuple2<CompileState, String>>(new Tuple2Impl<CompileState, String>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
        });
    }

    public static Tuple2<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return CompilerUtils.compileStatements(state, input, FunctionSegmentCompiler::compileFunctionSegment);
    }

    static Tuple2<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return CompilerUtils.compileOrPlaceholder(state, input, Lists.of(
                CompilerUtils::compileWhitespace,
                FunctionSegmentCompiler::compileEmptySegment,
                FunctionSegmentCompiler::compileBlock,
                FunctionSegmentCompiler::compileFunctionStatement,
                FunctionSegmentCompiler::compileReturnWithoutSuffix
        ));
    }
}