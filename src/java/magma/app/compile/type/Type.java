package magma.app.compile.type;

public interface Type {
    String generate();

    boolean isFunctional();

    boolean isVar();

    String generateBeforeName();

    String generateSimple();
}
