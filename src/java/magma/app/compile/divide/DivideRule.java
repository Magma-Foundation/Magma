package magma.app.compile.divide;

import jvm.api.collect.Lists;
import magma.api.result.Ok;
import magma.app.compile.Compiler;
import magma.app.compile.MapNode;
import magma.app.compile.rule.Rule;
import magma.api.collect.List_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.ParseState;

import static magma.app.compile.Compiler.HEADER;
import static magma.app.compile.Compiler.TARGET;

public record DivideRule(Rule child) implements Rule {
    public static Tuple<StringBuilder, StringBuilder> appendBuilders(Tuple<StringBuilder, StringBuilder> builders, Tuple<String, String> elements) {
        StringBuilder newLeft = builders.left().append(elements.left());
        StringBuilder newRight = builders.right().append(elements.right());
        return new Tuple<>(newLeft, newRight);
    }

    public static DivideState divideText(DivideState state, char c) {
        DivideState appended = state.appendChar(c);

        return divideSingleQuotes(appended, c)
                .orElseGet(() -> divideStatementChar(appended, c));
    }

    private static Option<DivideState> divideSingleQuotes(DivideState state, char c) {
        if (c != '\'') return new None<>();

        return state.append().flatMap(maybeSlash -> {
            Option<DivideState> divideStateOption;
            DivideState oldState = maybeSlash.left();
            if (maybeSlash.right() == '\\') {
                divideStateOption = oldState.appendAndDiscard();
            } else {
                divideStateOption = new Some<>(oldState);
            }

            return divideStateOption.flatMap(DivideState::appendAndDiscard);
        });
    }

    private static DivideState divideStatementChar(DivideState appended, char c) {
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '}' && appended.isShallow()) return appended.advance().exit();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }

    private Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        List_<Character> queue = Lists.fromString(input);

        DivideState current = new DivideState(queue);
        while (true) {
            Option<Tuple<DivideState, Character>> maybeNext = current.pop();
            if (maybeNext.isEmpty()) break;

            Tuple<DivideState, Character> tuple1 = maybeNext.orElse(new Tuple<>(current, '\0'));
            current = divideText(tuple1.left(), tuple1.right());
        }

        return current.advance().stream()
                .foldToResult(new Tuple<>(new StringBuilder(), new StringBuilder()), (output, segment) -> {
                    Rule rule = child();
                    return rule.parse(segment).flatMapValue(parsed -> rule.transform(state, parsed))
                            .flatMapValue(rule::generate).mapValue(result -> appendBuilders(output, result));
                })
                .mapValue(tuple -> new Tuple<>(tuple.left().toString(), tuple.right().toString()));
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        return new Ok<>(new MapNode().withString(Compiler.INPUT, input));
    }

    @Override
    public Result<MapNode, CompileError> transform(ParseState state, MapNode input) {
        return apply(state, input.find(Compiler.INPUT).orElse("")).mapValue(tuple -> {
            return new MapNode().withString(HEADER, tuple.left()).withString(TARGET, tuple.right());
        });
    }

    @Override
    public Result<Tuple<String, String>, CompileError> generate(MapNode node) {
        return new Ok<>(new Tuple<>(node.find(HEADER).orElse(""), node.find(TARGET).orElse("")));
    }
}