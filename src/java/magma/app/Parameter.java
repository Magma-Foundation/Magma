package magma.app;

import magma.api.option.Option;

interface Parameter {
    String generate();

    Option<Definition> asDefinition();
}
