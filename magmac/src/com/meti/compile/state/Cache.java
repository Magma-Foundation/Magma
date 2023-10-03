package com.meti.compile.state;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.List;
import com.meti.compile.node.Node;

public record Cache(List<Node> nodes) {
    public Cache() {
        this(ImmutableLists.empty());
    }

    public Cache add(State state) {
        return state.value()
                .map(value -> new Cache(nodes.addLast(value)))
                .unwrapOrElse(this);
    }

    public List<Node> values() {
        return nodes;
    }
}
