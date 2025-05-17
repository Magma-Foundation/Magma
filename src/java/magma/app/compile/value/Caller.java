package magma.app.compile.value;

import magma.api.option.Option;
import magma.app.io.Platform;

public interface Caller {
    String generate(Platform platform);

    Option<Value> findChild();
}
