package magma.app;

import magma.api.option.Option;

public interface Parameter {
    String generate();

    Option<Definition> asDefinition();
}
