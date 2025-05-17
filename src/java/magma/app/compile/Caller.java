package magma.app.compile;

import magma.api.option.Option;
import magma.app.Value;

public interface Caller {
    String generate();

    Option<Value> findChild();
}
