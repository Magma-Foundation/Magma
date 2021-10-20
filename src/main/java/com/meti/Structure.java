package com.meti;

import java.util.List;

public record Structure(String name, List<? extends Node> members) implements Node {
    @Override
    public boolean is(Type type) {
        throw new UnsupportedOperationException();
    }
}