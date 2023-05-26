package com.meti.node;

import java.util.List;

public abstract class Definition implements Node {
    public final List<Flag> flags;
    protected final String name;

    public Definition(String name, List<Flag> flags) {
        this.name = name;
        this.flags = flags;
    }

    public enum Flag {
        Public,
        Static,
        Class
    }
}
