package magma.app.compile.define;

import magma.app.io.Platform;

public interface FunctionHeader<S extends FunctionHeader<S>> {
    String generateWithAfterName(Platform platform, String afterName);

    boolean hasAnnotation(String annotation);

    S removeModifier(String modifier);

    S addModifier(String modifier);
}
