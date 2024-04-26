package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.Node;
import com.meti.node.NodePrototype;
import com.meti.option.None;
import com.meti.option.Option;

public class RequireBothSlices implements Rule {
    private final String before;
    private final String after;
    private final Rule content;

    public RequireBothSlices(String before, Rule content, String after) {
        this.before = before;
        this.after = after;
        this.content = content;
    }

    @Override
    public Option<NodePrototype> fromString(JavaString input) {
        return input.firstRangeOfSlice(before).and(input.lastRangeOfSlice(after)).flatMap(tuple -> {
            if (!tuple.a().startIndex().isStart()) return new None<>();
            if (!tuple.b().endIndex().isEnd()) return new None<>();

            var content = input.sliceBetween(tuple.a().endIndex(), tuple.b().startIndex());
            return this.content.fromString(content);
        });
    }

    @Override
    public Option<JavaString> fromNode(Node node) {
        return content.fromNode(node).map(value -> value.prependSlice(before).appendSlice(after));
    }
}
