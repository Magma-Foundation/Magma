package com.meti.compile.node;

public class EmptyNode implements Node{
    @Override
    public boolean is(Type type) {
        return type == Type.Empty;
    }
}
