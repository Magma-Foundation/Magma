package magmac.app.compile.rule.divide;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.iter.Iter;
import magmac.app.compile.rule.fold.Folder;

public record FoldingDivider(Folder folder) implements Divider {
    private static Option<DivideState> foldSingleQuotes(DivideState current, char c) {
        if ('\'' != c) {
            return new None<>();
        }

        return current.append(c)
                .popAndAppendToTuple()
                .flatMap((Tuple2<DivideState, Character> tuple) -> FoldingDivider.foldEscape(current, tuple.right()))
                .flatMap(DivideState::popAndAppendToOption);
    }

    private static Option<DivideState> foldEscape(DivideState current, char c) {
        if ('\\' == c) {
            return current.popAndAppendToOption();
        }
        else {
            return new Some<>(current);
        }
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if ('\"' != c) {
            return new None<>();
        }

        DivideState current = state.append('\"');
        while (true) {
            Option<Tuple2<DivideState, Character>> maybePopped = current.popAndAppendToTuple();
            if (maybePopped.isEmpty()) {
                break;
            }

            Tuple2<DivideState, Character> poppedTuple = maybePopped.orElse(null);
            current = poppedTuple.left();

            if ('\\' == poppedTuple.right()) {
                current = current.popAndAppendToOption().orElse(current);
            }
            if ('\"' == poppedTuple.right()) {
                break;
            }
        }

        return new Some<>(current);
    }

    @Override
    public Iter<String> divide(String input) {
        DivideState current = new MutableDivideState(input);
        while (true) {
            Option<Tuple2<DivideState, Character>> maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            current = maybePopped.orElse(null).left();
            char c = maybePopped.orElse(null).right();

            DivideState finalCurrent = current;
            current = FoldingDivider.foldSingleQuotes(current, c)
                    .or(() -> FoldingDivider.foldDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> this.folder.fold(finalCurrent, c));
        }

        return current.advance().iter();
    }

    @Override
    public String createDelimiter() {
        return this.folder.createDelimiter();
    }
}