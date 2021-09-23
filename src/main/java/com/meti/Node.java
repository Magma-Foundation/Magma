package com.meti;

public interface Node {
    Group group();

    enum Group {
        Content,
        Primitive
    }

    String renderMagma();

    String renderNative();
}
