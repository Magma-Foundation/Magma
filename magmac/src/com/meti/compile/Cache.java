package com.meti.compile;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.List;

public record Cache(List<Node> nodes) {
    public Cache() {
        this(ImmutableLists.empty());
    }

    public Cache(List<Node> nodes) {
        this.nodes = nodes;
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
