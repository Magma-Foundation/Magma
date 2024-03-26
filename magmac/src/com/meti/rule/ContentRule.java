package com.meti.rule;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.NodeAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class ContentRule implements Rule {
    private final String nodeType;
    private final String attributeName;
    private final int indent;

    public ContentRule(String attributeName, String nodeType, int indent) {
        this.nodeType = nodeType;
        this.attributeName = attributeName;
        this.indent = indent;
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if(input.isBlank()) return Optional.empty();
        return Optional.of(Collections.singletonMap(attributeName, new NodeAttribute(new Content(nodeType, input, indent))));
    }
}
