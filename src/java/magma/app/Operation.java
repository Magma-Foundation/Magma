package magma.app;

import magma.api.Type;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;

record Operation(Value left, String targetInfix, Value right) implements Value {
    @Override
    public String generate() {
        return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
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
        return Primitive.Unknown;
    }

    @Override
    public Option<String> generateAsEnumValue(final String structureName) {
        return new None<String>();
    }
}
