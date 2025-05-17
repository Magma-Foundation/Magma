package magma.app.compile.define;

import magma.api.option.Option;
import magma.app.io.Platform;

public interface Parameter {
    String generate(Platform platform);

    Option<Definition> asDefinition();
}
