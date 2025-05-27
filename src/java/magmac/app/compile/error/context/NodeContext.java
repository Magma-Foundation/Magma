package magmac.app.compile.error.context;

import magmac.app.compile.node.Node;

public record NodeContext(Node node) implements Context {
    @Override
    public String display() {
        return this.node.toString();
    }
}

