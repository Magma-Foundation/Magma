package com.meti;

public abstract class Definition implements Node {
    protected final String name;
    protected final boolean isPublic;

    public Definition(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }
}
