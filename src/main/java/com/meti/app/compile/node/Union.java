package com.meti.app.compile.node;

import com.meti.api.collect.java.List;

public class Union implements Type {
    private final List<Role> options;

    public Union(List<Role> options) {
        this.options = options;
    }

    @Override
    public boolean is(Role role) {
        return false;
    }
}
