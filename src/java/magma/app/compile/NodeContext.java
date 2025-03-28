package magma.app.compile;

public record NodeContext(Node node) implements Context {
    @Override
    public String display() {
        return node.display();
    }
}
