package com.meti;

public class CFunctionRenderer {
    String render(final String name, final String type, final String body) {
        return type + " " + name + "()" + body;
    }
}