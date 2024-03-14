package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Node;

public class ReturnNode implements Node {
    private final Node content;

    public ReturnNode(Node content) {
        this.content = content;
    }

    @Override
    public Option<String> render() {
        return content.render().map(value -> "return " + value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some.Some(new ReturnNode(value));
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some.Some(content);
    }

    @Override
    public String toString() {
        return "ReturnNode{" +
               "content=" + content +
               '}';
    }

    @Override
    public boolean is(String name) {
        return name.equals("return");
    }
}
