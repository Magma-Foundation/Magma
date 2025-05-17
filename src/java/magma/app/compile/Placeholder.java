package magma.app.compile;

import magma.api.Type;
import magma.api.option.None;
import magma.api.option.Option;
import magma.app.Definition;
import magma.app.Main;
import magma.app.Parameter;
import magma.app.Primitive;
import magma.app.Value;

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
    public Type resolve(final CompileState state) {
        return Primitive.Unknown;
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
    public Option<String> generateAsEnumValue(final String structureName) {
        return new None<String>();
    }
}
