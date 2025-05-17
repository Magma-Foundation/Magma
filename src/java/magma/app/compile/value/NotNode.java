package magma.app.compile.value;

import magma.api.Type;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.type.PrimitiveType;
import magma.app.io.Platform;

public record NotNode(String child) implements Value {
    @Override
    public String generate(Platform platform) {
        return this.child;
    }

    @Override
    public Option<Value> toValue() {
        return new Some<Value>(this);
    }

    @Override
    public Option<Value> findChild() {
        return new None<Value>();
    }

    public Type resolve(final CompileState state) {
        return PrimitiveType.Auto;
    }

    @Override
    public Option<String> generateAsEnumValue(final String structureName, Platform platform) {
        return new None<String>();
    }
}
