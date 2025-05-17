package magma.app.compile.value;

import magma.api.Type;
import magma.api.option.Option;
import magma.app.io.Platform;

public interface Value extends Argument, Caller {
    Option<String> generateAsEnumValue(String structureName, Platform platform);

    Type type();
}
