package magma.app.compile.text;

import magma.api.option.None;
import magma.api.option.Option;
import magma.app.compile.define.Definition;
import magma.app.compile.define.Parameter;

public record Whitespace() implements Parameter {
    @Override
    public String generate() {
        return "";
    }

    @Override
    public Option<Definition> asDefinition() {
        return new None<Definition>();
    }
}
