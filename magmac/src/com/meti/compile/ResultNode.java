package com.meti.compile;

import com.meti.api.collect.JavaList;
import com.meti.api.iterator.Iterator;
import com.meti.api.option.Option;
import com.meti.compile.rule.Rule;

import java.util.Objects;

public final class ResultNode implements Node {
    private final String type;
    private final Rule.Result evaluated;

    private ResultNode(String type, Rule.Result evaluated) {
        this.type = type;
        this.evaluated = evaluated;
    }

    public static Node createResultNode(String actualType, Rule.Result evaluated) {
        var text = evaluated.text();
        var nodes = evaluated.nodes()
                .iter()
                .map(tuple -> tuple.mapRight(Content::of))
                .collect(JavaList.collect());

        return new Node() {
            @Override
            public boolean is(String type) {
                return actualType.equals(type);
            }

            @Override
            public Option<String> getString(String name) {
                return text.get(name);
            }

            @Override
            public Iterator<Tuple<String, Node>> getNodes() {
                return nodes.iter();
            }

            @Override
            public Option<Node> withNode(String name, Node value) {
                return no;
            }

            @Override
            public Option<String> getValue() {
                return null;
            }
        };
    }

    @Override
    public boolean is(String type) {
        return this.type.equals(type);
    }

    @Override
    public Option<String> getString(String name) {
        return evaluated.text().get(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ResultNode) obj;
        return Objects.equals(this.type, that.type) &&
               Objects.equals(this.evaluated, that.evaluated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, evaluated);
    }

    @Override
    public String toString() {
        return "ResultNode[" +
               "type=" + type + ", " +
               "evaluated=" + evaluated + ']';
    }

}