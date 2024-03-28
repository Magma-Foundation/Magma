package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.NodeListAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

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

    private Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
var result = lex(input);
        return result;
    }
}
