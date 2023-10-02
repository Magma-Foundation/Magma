package com.meti.compile;

import com.meti.api.collect.JavaString;

public record MapNode(JavaString name) implements Node{
    @Override
    public boolean is(String name) {
        return this.name.equalsToSlice(name);
    }
}
