package com.meti.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class NodeSplitRule extends SplitRule {
    public NodeSplitRule(String propertyName, Rule childRule) {
        super(propertyName, childRule);
    }

    public static Rule Nodes(String propertyName, Rule childRule) {
        return new NodeSplitRule(propertyName, childRule);
    }

    private static SplitState processChar(char c, SplitState current) {
        if (c == '\"') {
            return current.toggleQuotes().append(c);
        }

        if (current.isWithinQuotes()) {
            return current.append(c);
        }

        if (c == ';' && current.depth == 0) return current.advance();
        if (c == '}' && current.depth == 1) {
            return current.append(c).exit().advance();
        }
        return switch (c) {
            case '{' -> current.enter().append(c);
            case '}' -> current.exit().append(c);
            default -> current.append(c);
        };
    }

    @Override
    public List<String> split(String input) {
        var current = new SplitState();
        for (int i = 0; i < input.length(); i++) {
            current = processChar(input.charAt(i), current);
        }

        return current.advance().lines.stream()
                .filter(value -> !value.isBlank())
                .collect(Collectors.toList());
    }

    @Override
    protected String computeDelimiter() {
        return "";
    }

    private record SplitState(int depth, ArrayList<String> lines, StringBuilder builder, boolean withinQuotes) {
        public SplitState() {
            this(0, new ArrayList<>(), new StringBuilder(), false);
        }

        private SplitState append(char c) {
            return new SplitState(depth, lines, this.builder.append(c), withinQuotes);
        }

        private SplitState exit() {
            return new SplitState(depth - 1, lines, builder, withinQuotes);
        }

        private SplitState enter() {
            return new SplitState(depth + 1, lines, builder, withinQuotes);
        }

        private SplitState advance() {
            var copy = new ArrayList<>(lines);
            copy.add(builder.toString());
            return new SplitState(depth, copy, new StringBuilder(), withinQuotes);
        }

        public boolean isWithinQuotes() {
            return withinQuotes;
        }

        public SplitState toggleQuotes() {
            return new SplitState(depth, lines, builder, !withinQuotes);
        }
    }
}
