package magma.app.compile.define;

import magma.api.collect.list.List;
import magma.app.io.Platform;

public interface FunctionHeader<S extends FunctionHeader<S>> {
    String generateWithDefinitions(Platform platform, List<Definition> definitions);

    boolean hasAnnotation(String annotation);

    S removeModifier(String modifier);

    S addModifierLast(String modifier);
}
