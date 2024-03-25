package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;

public interface Rule {
    Optional<Map<String, Attribute>> apply(String input);
}
