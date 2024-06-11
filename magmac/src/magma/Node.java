package magma;

public interface Node {
    Node withModifiers(String modifiers);

    String modifiers();

    String name();

    String content();
}
