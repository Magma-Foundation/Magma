package magma.app.compile.define;

public interface FunctionHeader<S extends FunctionHeader<S>> {
    String generateWithAfterName(String afterName);

    boolean hasAnnotation(String annotation);

    S removeModifier(String modifier);

    S addModifier(String modifier);
}
