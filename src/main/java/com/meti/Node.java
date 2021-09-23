package com.meti;

import java.util.stream.Stream;

public interface Node {
    String getValue();

    Group group();

    Stream<Node> streamTypes();

    Node withType(Node type);

    enum Group {
        Content,
        Implicit, Primitive
    }

    String renderMagma();

    String renderNative();
}
