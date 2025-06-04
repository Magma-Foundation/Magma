package magmac.app.compile.error.context;

public record StringContext(String value) implements Context {
    @Override
    public String display() {
        return this.value;
    }
}
