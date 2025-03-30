#include "FoldingDivider.h"
struct public FoldingDivider(struct Folder folder}{this.folder = folder;}struct List__String divide(struct String input}{List_<Character> collector = Streams.fromString(input).collect(new ListCollector<>());

        DividingState current = new MutableDividingState(collector);while(true) {
            Tuple<Boolean, Tuple<Character, DividingState>> maybeNext = current
                    .append()
                    .toTuple(new Tuple<>(, current));

            if(maybeNext.left()) {
                Tuple<Character, DividingState> result = maybeNext.right();
                current = folder.fold(result.right(), result.left());
            } else {
                break;
            }
        }

        return current.advance().segments();}struct String join(struct String current, struct String element}{return folder.join(current, element);}