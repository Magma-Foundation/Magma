package com.meti;

public record MagmaTraitNode(String prefixString, String name, String content) {
    String render() {
        return prefixString() + "trait " + name() + " " + content();
    }
}