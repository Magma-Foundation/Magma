package magma.app.compile.define;

public interface MethodHeader {
    String generateWithAfterName(String afterName);

    boolean hasAnnotation(String annotation);

    MethodHeader removeModifier(String modifier);
}
