package magma.compile.context;

public final class StringContext implements Context {
    private final String input;

    public StringContext(String input) {
        this.input = input;
    }

    @Override
    public String display() {
        return input;
    }
}