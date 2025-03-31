#include "DecoratedFolder.h"
magma.option.Option<magma.compile.rule.divide.DividingState> processSlash(magma.option.Tuple<char, magma.compile.rule.divide.DividingState> tuple){if (tuple.left() == '\\') {
            return tuple.right().appendAndDiscard();
        }else {
            return new Some<>(tuple.right());
        }
}
magma.compile.rule.divide.DividingState fold(magma.compile.rule.divide.DividingState state, char c){if (c == '\'') {
            return state.append(c)
                    .append()
                    .flatMap(DecoratedFolder::processSlash)
                    .flatMap(DividingState::appendAndDiscard)
                    .orElse(state);
        }if (c == '"') {
            DividingState current = state.append(c);
            while (true) {
                Tuple<Character, DividingState> tuple = current.append().orElse(new Tuple<>('\0', current));
                current = tuple.right();

                if (tuple.left() == '\\') current = current.appendAndDiscard().orElse(current);
                if (tuple.left() == '"') break;
            }

            return current;
        }return folder.fold(state, c);
}
String join(String current, String element){return folder.join(current, element);
}

