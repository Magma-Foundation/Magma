package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.*;

public class AndRule implements Rule {
    private final List<Rule> rules;

    public AndRule(Rule... rules) {
        this.rules = new ArrayList<>(List.of(rules));
    }

    public static Rule And(Rule... rules) {
        return new AndRule(rules);
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        return rules.stream()
                .map(rule -> rule.render(attributes))
                .reduce(Optional.of(""), (acc, optional) ->
                        acc.flatMap(accStr -> optional.map(accStr::concat)));
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        if (rules.isEmpty()) {
            return Optional.of(new Tuple<>(Optional.empty(), Collections.emptyMap()));
        }

        return lexRecursively(input, stack, 0);
    }

    private Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexRecursively(
            String input, Stack<String> stack, int index) {
        if (index >= rules.size()) {
            return Optional.of(new Tuple<>(Optional.empty(), Collections.emptyMap()));
        }

        Rule currentRule = rules.get(index);
        for (int i = 0; i <= input.length(); i++) {
            String leftPart = input.substring(0, i);
            String rightPart = input.substring(i);

            Optional<Tuple<Optional<String>, Map<String, Attribute>>> leftResult = currentRule.lex(leftPart, stack);
            if (leftResult.isPresent()) {
                Optional<Tuple<Optional<String>, Map<String, Attribute>>> rightResult = lexRecursively(rightPart, stack, index + 1);
                if (rightResult.isPresent()) {
                    Map<String, Attribute> combinedAttributes = new HashMap<>(leftResult.get().b());
                    combinedAttributes.putAll(rightResult.get().b());
                    return Optional.of(new Tuple<>(Optional.empty(), combinedAttributes));
                }
            }
        }

        return Optional.empty();
    }
}
