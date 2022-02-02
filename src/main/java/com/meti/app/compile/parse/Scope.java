package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;

public class Scope {
    private final List<Node> frame;

    public Scope() {
        this(List.createList());
    }

    public Scope(List<Node> frame) {
        this.frame = frame;
    }

    public Scope define(Node definition) {
        return new Scope(frame.add(definition));
    }

    boolean isDefined(String name) {
        return lookup(name).isPresent();
    }

    Option<Node> lookup(String name) {
        try {
            return frame.stream()
                    .filter(declaration -> declaration.apply(Attribute.Type.Name).asInput().equalsSlice(name))
                    .headOptionally();
        } catch (StreamException e) {
            return new None<>();
        }
    }
}
