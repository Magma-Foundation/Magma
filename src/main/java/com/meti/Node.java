package com.meti;

import java.util.stream.Stream;

public interface Node {
    Node getType();

    String getValue();

    Group group();

    Stream<Node> streamTypes();

    Node withType(Node type);

    enum Group {
        Content,
        Implicit, Declaration, Assignment, Primitive
    }

    String renderMagma();

    String renderNative();
}
