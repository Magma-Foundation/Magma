package magma.app;

interface MethodHeader<S extends MethodHeader<S>> {
    String generateWithAfterName(String afterName);

    boolean hasAnnotation(String annotation);

    S removeModifier(String modifier);

    S addModifier(String modifier);
}
