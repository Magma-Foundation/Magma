package magmac.app.compile.rule;

import magmac.app.compile.Context;
import magmac.app.compile.node.Node;

public record NodeContext(Node node) implements Context {
    @Override
    public String display() {
        return this.node.toString();
    }
}

