package com.meti;

public record JavaInterfaceNode(String prefixString, String name) {
    String renderJavaInterface() {
        return prefixString() + Compiler.INTERFACE_KEYWORD + name() + " {}";
    }
}