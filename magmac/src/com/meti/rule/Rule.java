package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;

public interface Rule {
    Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexImpl(String input);

    Optional<String> render(Map<String, Attribute> attributes);
}
