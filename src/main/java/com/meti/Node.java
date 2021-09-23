package com.meti;

import java.util.stream.Stream;

public interface Node {
    String getName();

    Node getType();

    String getValue();

    Group group();

    boolean isFlagged(Declaration.Flag flag);

    Stream<Node> streamTypes();

    Node withType(Node type);

    enum Group {
        Content,
        Implicit, Declaration, Assignment, Primitive
    }

    String renderMagma();

    String renderNative();
}
