package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public interface RuleResult {
    Optional<Tuple<NodeAttributes, Optional<String>>> unwrap();

    default boolean isValid() {
        return unwrap().isPresent();
    }

    default Optional<NodeAttributes> getAttributes() {
        return unwrap().map(Tuple::left);
    }

    default Optional<String> getName() {
        return unwrap().flatMap(Tuple::right);
    }
}
