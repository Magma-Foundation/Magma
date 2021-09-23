package com.meti.node;

import com.meti.ApplicationException;

import java.util.stream.Stream;

public interface Node {
    Attribute apply(Attribute.Type type) throws ApplicationException;

    Group group();

    boolean isFlagged(Declaration.Flag flag);

    String renderMagma();

    String renderNative();

    Stream<Node> streamTypes();

    Node withType(Node type);

    enum Group {
        Assignment,
        Declaration,
        Content,
        Implicit,
        Primitive
    }
}
