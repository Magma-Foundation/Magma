package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AndRule implements Rule {
    private final Rule left;
    private final Rule right;

    public AndRule(Rule left, Rule right) {
        this.left = left;
        this.right = right;
    }

    public static Rule And(Rule first, Rule second, Rule... more) {
        return Arrays.stream(more).reduce(new AndRule(first, second), AndRule::new);
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        for (int i = 0; i < input.length(); i++) {
            var left = input.substring(0, i);
            var right = input.substring(i);

            var leftOptional = this.left.apply(left);
            if (leftOptional.isPresent()) {
                var rightOptional = this.right.apply(right);

                if (rightOptional.isPresent()) {
                    var stringAttributeMap = new HashMap<>(leftOptional.get());
                    rightOptional.get().forEach((key, value) -> {
                        stringAttributeMap.merge(key, value, Attribute::merge);
                    });

                    return Optional.of(stringAttributeMap);
                }
            }
        }

        return Optional.empty();
    }
}
