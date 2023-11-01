package com.meti.compile;

import com.meti.api.collect.JavaMap;
import com.meti.api.collect.Map;
import com.meti.api.iterator.Iterator;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.rule.Rule;

public record MapNode(String actualType, Map<String, String> text, Map<String, Node> nodes) implements Node {

    public static Node create(String actualType, Rule.Result evaluated) {
        var text = evaluated.text();
        var nodes = evaluated.nodes()
                .iter()
                .map(tuple -> tuple.mapRight(Content::of))
                .collect(JavaMap.collect());

        return new MapNode(actualType, text, nodes);
    }

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
        if (nodes.hasKey(name)) {
            return Some.apply(new MapNode(actualType, text, nodes.put(name, value)));
        } else {
            return None.apply();
        }
    }

    @Override
    public Option<String> getValue() {
        return null;
    }
}
