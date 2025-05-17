package magma.app.compile.value;

import magma.app.compile.define.FunctionHeader;

public class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
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
