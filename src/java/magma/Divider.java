package magma;

public interface Divider {
    DivideState apply(DivideState state, char next);
}
