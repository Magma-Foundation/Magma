package magma.compile.context;

import magma.compile.Node;

public record NodeContext(Node value) implements Context {
    @Override
    public String display() {
        return value.display();
    }
}