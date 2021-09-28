package com.meti;

public record Block(Iterable<Node> children) implements Node {
    @Override
    public String render() {
        var builder = new StringBuilder();
        for (Node child : children) {
            builder.append(child.render());
        }

        return "{" + builder + "}";
    }
}
