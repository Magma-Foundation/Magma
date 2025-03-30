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
    public DividingState fold(DividingState current, char c) {
        if (c == '\'') {
            return current.append()
                    .flatMap(DecoratedFolder::processSlash)
                    .flatMap(DividingState::appendAndDiscard)
                    .orElse(current);
        }

        return folder.fold(current, c);
    }

    @Override
    public String join(String current, String element) {
        return folder.join(current, element);
    }
}
