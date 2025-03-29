package magma.compile.context;

public record StringContext(String value) implements Context {
    @Override
    public String display() {
        return value;
    }
}