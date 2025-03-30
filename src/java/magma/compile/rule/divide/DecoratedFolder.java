package magma.compile.rule.divide;

import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;

public record DecoratedFolder(Folder folder) implements Folder {
    private static Option<DividingState> processSlash(Tuple<Character, DividingState> tuple) {
        if (tuple.left() == '\\') {
            return tuple.right().appendAndDiscard();
        } else {
            return new Some<>(tuple.right());
        }
    }

    @Override
    public DividingState fold(DividingState state, char c) {
        if (c == '\'') {
            return state.append(c)
                    .append()
                    .flatMap(DecoratedFolder::processSlash)
                    .flatMap(DividingState::appendAndDiscard)
                    .orElse(state);
        }

        if (c == '"') {
            DividingState current = state.append(c);
            while (true) {
                Tuple<Character, DividingState> tuple = current.append().orElse(new Tuple<>('\0', current));
                current = tuple.right();

                if (tuple.left() == '\\') current = current.appendAndDiscard().orElse(current);
                if (tuple.left() == '"') break;
            }

            return current;
        }

        return folder.fold(state, c);
    }

    @Override
    public String join(String current, String element) {
        return folder.join(current, element);
    }
}
