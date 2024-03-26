package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.*;

public class AndRule implements Rule {
    private final Rule left;
    private final Rule right;

    public AndRule(Rule left, Rule right) {
        this.left = left;
        this.right = right;
    }

    public static Rule And(Rule first, Rule second, Rule... more) {
        var list = new ArrayList<>(List.of(first, second));
        list.addAll(Arrays.asList(more));

        var firstSlice = new ArrayList<>(list.subList(0, list.size() - 2));
        Collections.reverse(firstSlice);

        return firstSlice.stream().reduce(new AndRule(list.get(list.size() - 2), list.get(list.size() - 1)),
                (left1, right1) -> new AndRule(right1, left1));
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        if (input.isEmpty()) {
            if (left.lex(input).map(tuple1 -> tuple1.b()).isPresent() && right.lex(input).map(tuple -> tuple.b()).isPresent()) {
                return Optional.of(Collections.emptyMap());
            } else {
                return Optional.empty();
            }
        }

        for (int i = 0; i <= input.length(); i++) {
            var left = input.substring(0, i);
            var right = input.substring(i);

            var leftOptional = this.left.lex(left).map(tuple1 -> tuple1.b());
            if (leftOptional.isPresent()) {
                var rightOptional = this.right.lex(right).map(tuple -> tuple.b());

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

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        return left.render(attributes).flatMap(leftValue -> right.render(attributes).map(rightValue -> leftValue + rightValue));
    }
}
