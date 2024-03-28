package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.*;

public class OrRule implements Rule {
    private final Rule first;
    private final Rule second;
    private final Stack<String> stack = new Stack<>();

    public OrRule(Rule first, Rule second) {
        this.first = first;
        this.second = second;
    }

    public static Rule Or(Rule first, Rule second, Rule... more) {
        var list = new ArrayList<>(List.of(first, second));
        list.addAll(Arrays.asList(more));

        var firstSlice = new ArrayList<>(list.subList(0, list.size() - 2));
        Collections.reverse(firstSlice);

        return firstSlice.stream().reduce(new OrRule(list.get(list.size() - 2), list.get(list.size() - 1)),
                (left1, right1) -> new OrRule(right1, left1));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        if (this.stack.contains(input)) return Optional.empty();

        this.stack.push(input);
        var or = first.lex(input, stack).or(() -> second.lex(input, stack));
        this.stack.pop();

        return or;
    }
}
