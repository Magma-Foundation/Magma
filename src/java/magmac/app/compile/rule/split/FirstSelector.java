package magmac.app.compile.rule.split;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.Joiner;

public class FirstSelector implements Selector {
    @Override
    public Option<Tuple2<String, String>> select(List<String> list) {
        return list.popFirst().map(tuple -> {
            String joined = tuple.right().iter().collect(new Joiner(" ")).orElse("");
            return new Tuple2<String, String>(tuple.left(), joined);
        });
    }
}