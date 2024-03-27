package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeListAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class NodeElementRule implements Rule {
    private final String attributeName;
    private final Rule nodeRule;

    public NodeElementRule(String attributeName, Rule nodeRule) {
        this.attributeName = attributeName;
        this.nodeRule = nodeRule;
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
                var node = new MapNode(stringAttributeMap.a().orElseThrow(), stringAttributeMap.b());
                var attribute = new NodeListAttribute(Collections.singletonList(node));
                return Collections.singletonMap(attributeName, attribute);
            }).map(attributes -> new Tuple<>(Optional.empty(), attributes));
        }

        return result;
    }
}
