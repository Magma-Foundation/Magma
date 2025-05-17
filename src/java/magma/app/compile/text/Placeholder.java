package magma.app.compile.text;

import magma.api.Type;
import magma.api.option.None;
import magma.api.option.Option;
import magma.app.Main;
import magma.app.compile.define.Definition;
import magma.app.compile.define.Parameter;
import magma.app.compile.type.PrimitiveType;
import magma.app.compile.value.Value;
import magma.app.io.Platform;

public record Placeholder(String input) implements Parameter, Value, Type {
    @Override
    public String generate() {
        return Main.generatePlaceholder(this.input);
    }

    @Override
    public boolean isFunctional() {
        return false;
    }

    @Override
    public String generate(final Platform platform) {
        return this.generate();
    }

    @Override
    public Option<Value> findChild() {
        return new None<Value>();
    }

    @Override
    public Option<Definition> asDefinition() {
        return new None<Definition>();
    }

    @Override
    public Option<Value> toValue() {
        return new None<Value>();
    }

    @Override
    public boolean isVar() {
        return false;
    }

    @Override
    public String generateBeforeName() {
        return "";
    }

    @Override
    public Option<String> generateAsEnumValue(final String structureName, Platform platform) {
        return new None<String>();
    }

    @Override
    public Type type() {
        return PrimitiveType.Auto;
    }
}
