package magma;

public record NodeContext(MapNode node) implements Context {
    @Override
    public String display() {
        return node.display();
    }
}
