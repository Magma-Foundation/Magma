package magmac.app.compile.rule.split;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.Joiner;

public class FirstSelector implements Selector {

    private final String delimiter;

    public FirstSelector(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Option<Tuple2<String, String>> select(List<String> list) {
        return list.popFirst().map((Tuple2<String, List<String>> tuple) -> {
            String joined = tuple.right().iter().collect(new Joiner(this.delimiter)).orElse("");
            return new Tuple2<String, String>(tuple.left(), joined);
        });
    }
}