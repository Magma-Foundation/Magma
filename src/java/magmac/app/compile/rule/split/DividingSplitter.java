package magmac.app.compile.rule.split;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.divide.Divider;

public final class DividingSplitter implements Splitter {
    private final Divider divider;
    private final Selector selector;

    private DividingSplitter(Divider divider, Selector selector) {
        this.divider = divider;
        this.selector = selector;
    }

    public static Splitter Last(Divider divider) {
        return new DividingSplitter(divider, new LastSelector());
    }

    public static Splitter First(Divider divider) {
        return new DividingSplitter(divider, new FirstSelector());
    }

    @Override
    public Option<Tuple2<String, String>> split(String input) {
        List<String> list = this.divider.divide(input).collect(new ListCollector<>());
        return this.selector.select(list);
    }

    @Override
    public String createMessage() {
        return "Insufficient segments present";
    }

    @Override
    public String merge(String leftString, String rightString) {
        return leftString + " " + rightString;
    }
}
