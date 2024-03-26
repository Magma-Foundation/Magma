package com.meti.rule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

public class Rules {
    public static Rule Empty = input -> {
        if (input.isEmpty()) return Optional.of(Collections.emptyMap());
        else return Optional.empty();
    };

    public static Rule Any = input -> Optional.of(new HashMap<>());
}
