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

    private static RuntimeException getRuntimeException(String input) {
        return new RuntimeException("Failed to find name for input: " + input);
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexImpl(String input) {
        Optional<Tuple<Optional<String>, Map<String, Attribute>>> result;
        if (input.isBlank()) {
            result = Optional.empty();
        } else {
            result = nodeRule.lexImpl(input).<Map<String, Attribute>>map(stringAttributeMap -> {
                var name = stringAttributeMap.a().orElseThrow(() -> getRuntimeException(input));
                var node = new MapNode(name, stringAttributeMap.b());
                var attribute = new NodeAttribute(node);
                return Collections.singletonMap(attributeName, attribute);
            }).map(attributes -> new Tuple<>(Optional.empty(), attributes));
        }

        return result;
    }
}
