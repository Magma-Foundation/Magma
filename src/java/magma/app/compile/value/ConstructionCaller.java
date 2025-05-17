package magma.app.compile.value;

import magma.api.option.None;
import magma.api.option.Option;
import magma.app.io.Platform;

public record ConstructionCaller(String right, Platform platform) implements Caller {
    @Override
    public String generate() {
        if (Platform.Magma == this.platform) {
            return this.right;
        }
        return "new " + this.right;
    }

    @Override
    public Option<Value> findChild() {
        return new None<Value>();
    }
}
