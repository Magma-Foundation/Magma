package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class NodeRule implements Rule {
    private final String attributeName;
    private final Rule nodeRule;

    public NodeRule(String attributeName, Rule nodeRule) {
        this.attributeName = attributeName;
        this.nodeRule = nodeRule;
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        if (input.isBlank()) return Optional.empty();

        return nodeRule.lex(input).<Map<String, Attribute>>map(stringAttributeMap -> {
            var node = new MapNode(stringAttributeMap.a().orElseThrow(), stringAttributeMap.b());
            var attribute = new NodeAttribute(node);
            return Collections.singletonMap(attributeName, attribute);
        }).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }
}
