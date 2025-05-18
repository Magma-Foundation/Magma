package magma.app.compile.define;

public class ConstructorHeader implements MethodHeader {
    @Override
    public String generateWithAfterName(String afterName) {
        return "constructor " + afterName;
    }

    @Override
    public boolean hasAnnotation(String annotation) {
        return false;
    }

    @Override
    public MethodHeader removeModifier(String modifier) {
        return this;
    }
}
