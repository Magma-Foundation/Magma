package magma.app.compile.rule;

import magma.api.result.Tuple;

public interface Output {
    Tuple<String, String> toTuple();

    Output with(String type, String content);
}
