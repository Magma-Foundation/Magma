package magma.compile.context;

import jvm.collect.string.JavaString;
import magma.compile.Node;

public record NodeContext(Node value) implements Context {
    private String display0() {
        return value.display();
    }

    @Override
    public JavaString display() {
        return new JavaString(display0());
    }
}