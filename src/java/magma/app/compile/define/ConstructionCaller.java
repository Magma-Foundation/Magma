package magma.app.compile.define;

import magma.api.option.None;
import magma.api.option.Option;
import magma.app.compile.value.Caller;
import magma.app.compile.value.Value;

public record ConstructionCaller(String right) implements Caller {
    @Override
    public String generate() {
        return "new " + this.right;
    }

    @Override
    public Option<Value> findChild() {
        return new None<Value>();
    }
}
