package magma.app.compile.value;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.Main;
import magma.app.compile.CompileState;
import magma.app.compile.type.Type;

public record Operation(Value left, String targetInfix, Value right) implements Value {
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

    public Type resolve(CompileState state) {
        return Main.Primitive.Unknown;
    }

    @Override
    public Option<String> generateAsEnumValue(String structureName) {
        return new None<String>();
    }
}
