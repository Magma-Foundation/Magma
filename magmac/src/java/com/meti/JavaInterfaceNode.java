package com.meti;

public record JavaInterfaceNode(String prefixString, String name) {
    String renderJavaInterface() {
        return prefixString() + Lang.INTERFACE_KEYWORD + name() + " {}";
    }
}