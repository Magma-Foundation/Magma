package magmac.app.compile.rule.split;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.divide.Divider;

public record DividingSplitter(Divider divider) implements Splitter {
    @Override
    public Option<Tuple2<String, String>> split(String input) {
        return this.divider.divide(input).collect(new ListCollector<>())
                .popLast()
                .map(tuple -> {
                    String joined = tuple.left().iter().collect(new Joiner(" ")).orElse("");
                    return new Tuple2<>(joined, tuple.right());
                });
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
