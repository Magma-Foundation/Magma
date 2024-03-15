package com.meti.compile.attempt;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Node;

public class TryNode implements Node {
    private final Node content;
    private final int indent;

    public TryNode(Node content, int indent) {
        this.content = content;
        this.indent = indent;
    }

    @Override
    public Option<String> render() {
        return content.render().map(value -> "\n" + "\t".repeat(indent) + "try " + value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some.Some(new TryNode(value, indent));
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some.Some(content);
    }

    @Override
    public boolean is(String name) {
        return name.equals("try");
    }
}
