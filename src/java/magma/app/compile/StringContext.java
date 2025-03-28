package magma.app.compile;

public record StringContext(String context) implements Context {
    @Override
    public String display() {
        return context;
    }
}