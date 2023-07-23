package com.meti.app.compile.imports;

import com.meti.app.compile.Node;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Import(String_ parent, String_ child) implements Node {
    public String_ render() {
        return fromSlice("import { ")
                .appendOwned(child())
                .append(" } from ")
                .appendOwned(parent())
                .append(";\n");
    }
}