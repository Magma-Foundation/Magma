package magma.app.compile.value;

import magma.app.compile.define.FunctionHeader;
import magma.app.io.Platform;

public class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
    @Override
    public String generateWithAfterName(Platform platform, final String afterName) {
        return "constructor " + afterName;
    }

    @Override
    public boolean hasAnnotation(final String annotation) {
        return false;
    }

    @Override
    public ConstructorHeader removeModifier(final String modifier) {
        return this;
    }

    @Override
    public ConstructorHeader addModifierLast(final String modifier) {
        return this;
    }
}
