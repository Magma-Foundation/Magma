package magma.app.compile.split;

import magma.api.Tuple2;
import magma.api.option.Option;

public interface Splitter {
    Option<Tuple2<String, String>> apply(String string);
}
