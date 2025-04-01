#include "FoldingDivider.h"
public FoldingDivider(Folder folder){this.folder = folder;
}
List_<String> divide(String input){List_<Character> collector = Streams.fromString(input).collect(new ListCollector<>());

        DividingState current = new MutableDividingState(collector);while(true) {
            Tuple<Boolean, Tuple<Character, DividingState>> maybeNext = current
                    .pop()
                    .toTuple(new Tuple<>('\0', current));

            if(maybeNext.left()) {
                Tuple<Character, DividingState> result = maybeNext.right();
                current = folder.fold(result.right(), result.left());
            } else {
                break;
            }
        }return current.advance().segments();
}
String join(String current, String element){return folder.join(current, element);
}
