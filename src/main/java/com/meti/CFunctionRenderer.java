package com.meti;

public class CFunctionRenderer {
    String render(final String name, final String type, final String body) {
        return new FieldRenderer().render(name, type) + "()" + body;
    }
}