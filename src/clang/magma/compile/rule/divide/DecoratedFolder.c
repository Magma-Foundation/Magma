#include "DecoratedFolder.h"
struct Option_DividingState processSlash(struct Tuple_Character_DividingState tuple){if (tuple.left() == '\\') {
            return tuple.right().appendAndDiscard();
        }else {
            return new Some<>(tuple.right());
        }
}
struct DividingState fold(struct DividingState state, struct char c){if (c == '\'') {
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
struct String join(struct String current, struct String element){return folder.join(current, element);
}

