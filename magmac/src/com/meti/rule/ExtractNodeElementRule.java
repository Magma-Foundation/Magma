package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.NodeListAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class ExtractNodeElementRule implements Rule {
    private final String nodeType;
    private final String attributeName;
    private final int indent;

    public ExtractNodeElementRule(String attributeName, String nodeType, int indent) {
        this.nodeType = nodeType;
        this.attributeName = attributeName;
        this.indent = indent;
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        return Optional.of(Collections.singletonMap(attributeName, new NodeListAttribute(Collections.singletonList(new Content(nodeType, input, indent)))));
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> apply(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }
}
