package magma.compile.context;

import jvm.collect.string.JavaString;
import magma.collect.string.String_;
import magma.compile.Node;

public record NodeContext(Node value) implements Context {
    private String display0() {
        return value.display();
    }

    @Override
    public String_ display() {
        return new JavaString(display0());
    }
}