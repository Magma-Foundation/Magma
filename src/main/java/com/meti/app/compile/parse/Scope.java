package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;

public class Scope {
    private final List<List<Node>> frames;

    public Scope() {
        this(List.createList());
    }

    public Scope(List<List<Node>> frames) {
        this.frames = frames;
    }

    public Scope define(Node definition) {
        return new Scope(frames.ensure(List::createList).mapLast(last -> last.add(definition)));
    }

    public Scope enter() {
        return new Scope(frames.add(List.createList()));
    }

    public Scope exit() {
        return new Scope(frames.pop());
    }

    boolean isDefined(String name) {
        return lookup(name).isPresent();
    }

    Option<Node> lookup(String name) {
        try {
            return frames.stream()
                    .flatMap(List::stream)
                    .filter(declaration -> declaration.apply(Attribute.Type.Name)
                            .asInput()
                            .equalsSlice(name))
                    .headOptionally();
        } catch (StreamException e) {
            return new None<>();
        }
    }

    public boolean isLevel() {
        return frames.size() <= 1;
    }
}
