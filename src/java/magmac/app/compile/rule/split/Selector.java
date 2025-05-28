package magmac.app.compile.rule.split;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;

public interface Selector {
    Option<Tuple2<String, String>> select(List<String> list);
}
