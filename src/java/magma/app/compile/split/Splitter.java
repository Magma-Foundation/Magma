package magma.app.compile.split;

import magma.api.Tuple2;
import magma.api.option.Option;

import java.util.function.Function;

public interface Splitter {
    Option<Tuple2<String, String>> apply(String string);
}
