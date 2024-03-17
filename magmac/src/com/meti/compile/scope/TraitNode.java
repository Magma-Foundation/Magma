package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public class TraitNode implements Node {
    private final JavaString name;
    private final Node content;

    public TraitNode(JavaString name, Node content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public Option<String> render() {
        return Some.Some("trait " + name + " " + content.render().orElse(""));
    }

    @Override
    public boolean is(String name) {
        return name.equals("interface");
    }
}
