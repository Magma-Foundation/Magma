package magma.compile.rule.divide;

import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.option.Tuple;

public class FoldingDivider implements Divider {
    private final Folder folder;

    public FoldingDivider(Folder folder) {
        this.folder = folder;
    }

    @Override
    public List_<String> divide(String input) {
        List_<Character> collector = Streams.fromString(input).collect(new ListCollector<>());

        DividingState current = new MutableDividingState(collector);
        while(true) {
            Tuple<Boolean, Tuple<Character, DividingState>> maybeNext = current
                    .append()
                    .toTuple(new Tuple<>('\0', current));

            if(maybeNext.left()) {
                Tuple<Character, DividingState> result = maybeNext.right();
                current = folder.fold(result.right(), result.left());
            } else {
                break;
            }
        }

        return current.advance().segments();
    }

    @Override
    public String join(String current, String element) {
        return folder.join(current, element);
    }
}