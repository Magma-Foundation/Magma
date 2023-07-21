package com.meti.app;

import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Import(String_ parent, String_ child) implements Renderable {
    @Override
    public String_ render() {
        return fromSlice("import { ")
                .appendOwned(child())
                .append(" } from ")
                .appendOwned(parent())
                .append(";\n");
    }
}