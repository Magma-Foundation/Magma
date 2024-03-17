package com.meti.compile.node;

import com.meti.collect.option.Option;
import com.meti.compile.magma.MagmaRenderer;

import static com.meti.collect.option.None.None;

public interface Node {
    @Deprecated
    default Option<String> render() {
        return new MagmaRenderer(this).render();
    }

    boolean is(String name);

    default Option<Attribute> apply(String name) {
        return None();
    }

    default Option<Node> with(String name, Attribute attribute) {
        return None();
    }
}
