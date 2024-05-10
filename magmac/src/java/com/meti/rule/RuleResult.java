package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.List;
import java.util.Optional;

public interface RuleResult {
    @Deprecated
    Optional<Tuple<NodeAttributes, Optional<String>>> unwrap();

    boolean isValid();

    Optional<NodeAttributes> getAttributes();

    Optional<String> getType();

    RuleResult withType(String type);

    List<RuleResult> getCauses();

    RuleException toException();
}
