package magma.api;

public interface Type {
    String generate();

    boolean isFunctional();

    boolean isVar();

    String generateBeforeName();
}
