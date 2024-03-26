package com.meti.rule;

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

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if (input.isBlank()) return Optional.empty();

        return nodeRule.apply(input).map(stringAttributeMap -> {
            var node = new MapNode(nodeType, stringAttributeMap);
            var attribute = new NodeAttribute(node);
            return Collections.singletonMap(attributeName, attribute);
        });
    }
}
