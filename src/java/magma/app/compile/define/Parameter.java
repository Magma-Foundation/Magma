package magma.app.compile.define;

import magma.api.option.Option;

public interface Parameter {
    String generate();

    Option<Definition> asDefinition();
}
