package magma.compile.context;

import magma.compile.MapNode;

public record NodeContext(MapNode node) implements Context {
    @Override
    public String display() {
        return node.display();
    }
}
