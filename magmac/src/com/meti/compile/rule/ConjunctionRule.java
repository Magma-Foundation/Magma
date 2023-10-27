package com.meti.compile.rule;

import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;

import java.util.ArrayList;
import java.util.List;

public record ConjunctionRule(Rule left, Rule right) implements Rule {
    public static Rule join(Rule first, Rule... more) {
        var current = first;
        for (Rule rule : more) {
            current = new ConjunctionRule(current, rule);
        }
        return current;
    }

    @Override
    public Option<List<Result>> evaluate(String input) {
        var results = new ArrayList<Result>();
        for (int i = 0; i < input.length(); i++) {
            var left = input.substring(0, i);
            var right = input.substring(i);

            var leftResults = this.left.evaluate(left);
            var rightResults = this.right.evaluate(right);
            leftResults.and(rightResults).ifPresent(tuple -> {
                var leftArray = tuple.a();
                var rightArray = tuple.b();
                for (Result leftElement : leftArray) {
                    for (Result rightElement : rightArray) {
                        results.add(leftElement.add(rightElement));
                    }
                }
            });
        }

        if (results.isEmpty()) {
            return None.apply();
        } else {
            return Some.apply(results);
        }
    }
}
