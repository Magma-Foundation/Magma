package magma;

public final class ClassNode implements Node {
    private final String modifiers;
    private final String name;
    private final String content;

    public ClassNode(String modifiers, String name, String content) {
        this.modifiers = modifiers;
        this.name = name;
        this.content = content;
    }

    @Override
    public Node withModifiers(String modifiers) {
        return new ClassNode(modifiers, name(), content());
    }

    @Override
    public String modifiers() {
        return modifiers;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String content() {
        return content;
    }
}