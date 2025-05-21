package magma.app;

import magma.api.Tuple2;
import magma.api.collect.list.List;
import magma.api.option.Option;

import java.util.function.Function;

public interface Selector {
    Option<Tuple2<String, String>> apply(List<String> stringList);
}
