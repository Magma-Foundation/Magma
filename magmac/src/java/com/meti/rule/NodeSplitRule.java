package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.node.NodeListAttribute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public record NodeSplitRule(String propertyName, Rule childRule) implements Rule {
    public static ArrayList<String> split(String input) {
        var current = new SplitState();
        for (int i = 0; i < input.length(); i++) {
            current = processChar(input.charAt(i), current);
        }

        return current.advance().lines;
    }

    public static Rule Nodes(String propertyName, Rule childRule) {
        return new NodeSplitRule(propertyName, childRule);
    }

    private static SplitState processChar(char c, SplitState current) {
        if(c == '\"') {
            return current.toggleQuotes().append(c);
        }

        if(current.isWithinQuotes()){
            return current.append(c);
        }

        if (c == ';' && current.depth == 0) return current.advance();
        if(c == '}' && current.depth == 1) {
            return current.append(c).exit().advance();
        }
        return switch (c) {
            case '{' -> current.enter().append(c);
            case '}' -> current.exit().append(c);
            default -> current.append(c);
        };
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        var input = split(value);
        var output = new ArrayList<MapNode>();

        for (String inputLine : input) {
            var optional = childRule.apply(inputLine);
            if (optional.isEmpty()) return Optional.empty();
            var tuple = optional.get();
            var left = tuple.left();
            var right = tuple.right();
            if (right.isEmpty()) return Optional.empty();

            output.add(new MapNode(right.get(), left));
        }

        var attributes = new NodeAttributes(Map.of(propertyName, new NodeListAttribute(output)));
        return Optional.of(new Tuple<>(attributes, Optional.empty()));
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
