package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class Rules {
    public static Rule Empty = new Rule() {
        @Override
        public Optional<Map<String, Attribute>> apply(String input) {
            if(input.isEmpty()) return Optional.of(Collections.emptyMap());
            else return Optional.empty();
        }
    };
}
