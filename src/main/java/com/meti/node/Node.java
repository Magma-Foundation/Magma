package com.meti.node;

import com.meti.ApplicationException;

import java.util.List;
import java.util.stream.Stream;

public interface Node {
    Attribute apply(Attribute.Type type) throws ApplicationException;

    Group group();

    boolean isFlagged(Declaration.Flag flag);

    String renderMagma();

    String renderNative();

    Stream<Node> streamTypes();

    Stream<Node> streamNodes();

    Stream<Node> streamNodeGroups();

    Node withNodeGroup(List<Node> children);

    Node withNode(Node node);

    Node withType(Node type);

    enum Group {
        Assignment,
        Declaration,
        Content,
        Implicit,
        Primitive
    }
}
