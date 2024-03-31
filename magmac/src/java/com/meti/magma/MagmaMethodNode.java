package com.meti.magma;

import com.meti.node.Renderable;

import java.util.Optional;

public final class MagmaMethodNode implements Renderable {
    private final String prefix;
    private final String name;
    private final Optional<String> type;
    private final String content;
    private final String exceptionString;

    public MagmaMethodNode(String prefix, String name, Optional<String> type, String content, String exceptionString) {
        this.prefix = prefix;
        this.name = name;
        this.type = type;
        this.content = content;
        this.exceptionString = exceptionString;
    }

    public String render() {
        var typeString = type.map(inner -> " : " + inner).orElse("");
        return prefix + "\n\tdef " + name + "()" + typeString + exceptionString + " => " + content;
    }
}