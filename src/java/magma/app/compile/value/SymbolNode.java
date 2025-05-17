package magma.app.compile.value;

import magma.api.Type;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.type.PrimitiveType;
import magma.app.io.Platform;

public record SymbolNode(String value) implements Value, Type {
    @Override
    public String generate(final Platform platform) {
        return this.value;
    }

    @Override
    public Type resolve(final CompileState state) {
        return state.resolve(this.value).orElse(PrimitiveType.Unknown);
    }

    @Override
    public Option<Value> toValue() {
        return new Some<Value>(this);
    }

    @Override
    public Option<Value> findChild() {
        return new None<Value>();
    }

    @Override
    public String generate() {
        return this.value;
    }

    @Override
    public boolean isFunctional() {
        return false;
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
}
