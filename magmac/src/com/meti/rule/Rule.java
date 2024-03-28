package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public interface Rule {
    Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack);

    Optional<String> render(Map<String, Attribute> attributes);
}
