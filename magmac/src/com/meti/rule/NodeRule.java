package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class NodeRule implements Rule {
    private final String nodeType;
    private final String attributeName;
    private final Rule nodeRule;

    public NodeRule(String attributeName, String nodeType, Rule nodeRule) {
        this.nodeType = nodeType;
        this.attributeName = attributeName;
        this.nodeRule = nodeRule;
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        if (input.isBlank()) return Optional.empty();

        return nodeRule.apply(input).map(tuple -> tuple.b()).map(stringAttributeMap -> {
            var node = new MapNode(nodeType, stringAttributeMap);
            var attribute = new NodeAttribute(node);
            return Collections.singletonMap(attributeName, attribute);
        });
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> apply(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }
}
