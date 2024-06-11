package magma;

public record ClassNode(String modifiers, String name, String content) {
    ClassNode withModifiers(String modifiers) {
        return new ClassNode(modifiers, name(), content());
    }
}