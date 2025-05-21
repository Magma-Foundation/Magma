package magma.app.compile.select;

import magma.api.Tuple2;
import magma.api.collect.list.List;
import magma.api.option.Option;

public interface Selector {
    Option<Tuple2<String, String>> apply(List<String> stringList);
}
