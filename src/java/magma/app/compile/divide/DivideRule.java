package magma.app.compile.divide;

import jvm.api.collect.Lists;
import magma.app.compile.Rule;
import magma.api.collect.List_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.ParseState;

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

    @Override
    public Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        List_<Character> queue = Lists.fromString(input);

        DivideState current = new DivideState(queue);
        while (true) {
            Option<Tuple<DivideState, Character>> maybeNext = current.pop();
            if (maybeNext.isEmpty()) break;

            Tuple<DivideState, Character> tuple1 = maybeNext.orElse(new Tuple<>(current, '\0'));
            current = divideText(tuple1.left(), tuple1.right());
        }

        return current.advance().stream()
                .foldToResult(new Tuple<>(new StringBuilder(), new StringBuilder()), (output, segment) -> child().apply(state, segment).mapValue(result -> appendBuilders(output, result)))
                .mapValue(tuple -> new Tuple<>(tuple.left().toString(), tuple.right().toString()));
    }
}