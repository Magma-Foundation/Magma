package com.meti;

public record ImportNode(String parent, String child) implements Node {
    @Override
    public Option<String> getChild() {
        return Some.apply(child);
    }

    @Override
    public Option<String> getParent() {
        return Some.apply(parent);
    }
}