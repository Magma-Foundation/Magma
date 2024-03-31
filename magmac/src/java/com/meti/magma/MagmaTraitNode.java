package com.meti.magma;

public record MagmaTraitNode(String prefixString, String name, String content) {
    public String render() {
        return prefixString() + "trait " + name() + " " + content();
    }
}