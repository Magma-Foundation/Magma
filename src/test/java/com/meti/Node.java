package com.meti;

public interface Node {
    boolean is(Import.Type type);

    Attribute apply(Attribute.Type type);
}
