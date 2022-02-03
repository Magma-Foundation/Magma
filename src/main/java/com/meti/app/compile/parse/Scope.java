package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.json.AbstractJSONable;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;

import java.util.Objects;

public class Scope extends AbstractJSONable {
    private final List<List<Node>> frames;
    private final List<Node> parents;

    public Scope() {
        this(List.createList(), List.createList());
    }

    public Scope(List<List<Node>> frames, List<Node> parents) {
        this.frames = frames;
        this.parents = parents;
    }

    public Scope define(Node definition) {
        return new Scope(frames.ensure(List::createList).mapLast(last -> last.add(definition)), List.createList());
    }

    public Scope enter(Node node) {
        var newParents = parents.add(node);
        return new Scope(frames.add(List.createList()), newParents);
    }

    public Scope exit() {
        return new Scope(frames.pop(), List.createList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(frames);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scope scope)) return false;
        return Objects.equals(frames, scope.frames);
    }

    boolean isDefined(String name) {
        return lookup(name).isPresent();
    }

    Option<Node> lookup(String name) {
        try {
            return frames.stream()
                    .flatMap(List::stream)
                    .filter(declaration -> declaration.apply(Attribute.Category.Name)
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

    @Override
    public JSONNode toJSON() throws JSONException {
        try {
            return frames.stream().foldRight(new ArrayNode.Builder(), (builder, nodeList) -> {
                try {
                    return builder.addObject(nodeList.stream().foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addJSON).build());
                } catch (StreamException e) {
                    throw new JSONException(e);
                }
            }).build();
        } catch (StreamException e) {
            throw new JSONException(e);
        }
    }

    public List<Node> getParents() {
        return parents;
    }
}
