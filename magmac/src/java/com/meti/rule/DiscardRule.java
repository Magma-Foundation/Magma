package com.meti.rule;

import com.meti.Tuple;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class DiscardRule implements Rule {
    public static final Rule Discard = new DiscardRule();

    private DiscardRule() {
    }

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value) {
        return Optional.of(new Tuple<>(Collections.emptyMap(), Optional.empty()));
    }
}
