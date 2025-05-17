package magma.app;

import magma.api.Type;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;

record Access(Value child, String property) implements Value {
    @Override
    public String generate() {
        return this.child.generate() + "." + this.property;
    }

    @Override
    public Option<Value> toValue() {
        return new Some<Value>(this);
    }

    @Override
    public Option<Value> findChild() {
        return new Some<Value>(this.child);
    }

    @Override
    public Type resolve(final CompileState state) {
        return Primitive.Unknown;
    }

    @Override
    public Option<String> generateAsEnumValue(final String structureName) {
        return new None<String>();
    }
}
