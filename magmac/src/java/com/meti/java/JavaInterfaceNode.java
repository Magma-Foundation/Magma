package com.meti.java;

import com.meti.Lang;

public record JavaInterfaceNode(String prefixString, String name) {
    public String renderJavaInterface() {
        return prefixString() + Lang.INTERFACE_KEYWORD + name() + " {}";
    }
}