package com.meti.compile;

import com.meti.compile.node.Node;

public record CMagmaTransformer() {
    Node transformStage(Node node) {
        return node;
    }
}
