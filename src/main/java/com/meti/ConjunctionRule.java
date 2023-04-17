package com.meti;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConjunctionRule implements Rule {
    private final Rule before;
    private final Rule after;

    public ConjunctionRule(Rule before, Rule after) {
        this.before = before;
        this.after = after;
    }

    @Override
    public Optional<Map<String, String>> extract(String value) {
        for (int i = 0; i < value.length(); i++) {
            var before = value.substring(0, i);
            var after = value.substring(i);

            var beforeResult = this.before.extract(before);
            var afterResult = this.after.extract(after);

            if(beforeResult.isPresent() && afterResult.isPresent()) {
                var copy = new HashMap<>(beforeResult.get());
                copy.putAll(afterResult.get());
                return Optional.of(copy);
            }
        }

        return Optional.empty();
    }
}
