package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.*;

public class AndRule implements Rule {
    private final Rule left;
    private final Rule right;
    private final Stack<Tuple<String, String>> stack = new Stack<>();

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

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        return left.render(attributes).flatMap(leftValue -> right.render(attributes).map(rightValue -> leftValue + rightValue));
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        if (input.isEmpty()) {
            if (left.lex(input, stack).map(Tuple::b).isPresent() && right.lex(input, stack).map(Tuple::b).isPresent()) {
                return Optional.of(new Tuple<>(Optional.empty(), Collections.emptyMap()));
            } else {
                return Optional.empty();
            }
        }

        Optional<Map<String, Attribute>> result1 = Optional.empty();
        for (int i = 0; i <= input.length(); i++) {
            var left1 = input.substring(0, i);
            var right1 = input.substring(i);

            var leftOptional = this.left.lex(left1, stack).map(Tuple::b);
            if (leftOptional.isPresent()) {
                var rightOptional = this.right.lex(right1, stack).map(Tuple::b);

                if (rightOptional.isPresent()) {
                    var stringAttributeMap = new HashMap<>(leftOptional.get());
                    rightOptional.get().forEach((key, value) -> stringAttributeMap.merge(key, value, Attribute::merge));

                    result1 = Optional.of(stringAttributeMap);
                }
            }
        }

        return result1.map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }
}
