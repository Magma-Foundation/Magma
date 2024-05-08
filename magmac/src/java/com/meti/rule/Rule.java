package com.meti.rule;

import com.meti.Tuple;

import java.util.Map;
import java.util.Optional;

public interface Rule {
    Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value);
}

