package com.meti.compile.clazz;

import com.meti.compile.Node;

public record ClassNode(String name, String body) implements Node {
    @Override
    public boolean is(String name) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}