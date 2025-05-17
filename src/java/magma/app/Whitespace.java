package magma.app;

import magma.api.option.None;
import magma.api.option.Option;

record Whitespace() implements Parameter {
    @Override
    public String generate() {
        return "";
    }

    @Override
    public Option<Definition> asDefinition() {
        return new None<Definition>();
    }
}
