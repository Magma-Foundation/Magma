package magma.app.compile.fold;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.DivideState;

public record DecoratedFolder(Folder folder) implements Folder {
    private static Option<DivideState> foldSingleQuotes(DivideState state, char c) {
        if ('\'' != c) {
            return new None<DivideState>();
        }

        return state.append(c)
                .popAndAppendToTuple()
                .flatMap(DecoratedFolder::foldEscaped)
                .flatMap((DivideState state1) -> state1.popAndAppendToOption());
    }

    private static Option<DivideState> foldEscaped(Tuple2<DivideState, Character> tuple) {
        var state = tuple.left();
        var c = tuple.right();

        if ('\\' == c) {
            return state.popAndAppendToOption();
        }

        return new Some<DivideState>(state);
    }

    private static Option<DivideState> foldDoubleQuotes(DivideState state, char c) {
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

    @Override
    public DivideState apply(DivideState divideState, char c) {
        return DecoratedFolder.foldSingleQuotes(divideState, c)
                .or(() -> DecoratedFolder.foldDoubleQuotes(divideState, c))
                .orElseGet(() -> this.folder.apply(divideState, c));
    }
}