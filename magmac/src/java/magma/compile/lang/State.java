package magma.compile.lang;

public record State(int depth) {
    public State exit() {
        return new State(depth - 1);
    }

    public State enter() {
        return new State(depth + 1);
    }

    public boolean isDefined(String value) {
        return false;
    }
}
