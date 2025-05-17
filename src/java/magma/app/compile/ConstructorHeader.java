package magma.app.compile;

import magma.app.MethodHeader;

public class ConstructorHeader implements MethodHeader<ConstructorHeader> {
    @Override
    public String generateWithAfterName(final String afterName) {
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
    public ConstructorHeader addModifier(final String modifier) {
        return this;
    }
}
