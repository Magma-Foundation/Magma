package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Some.Some;

public class FieldNode implements Node {
    private final Node parent;
    private final JavaString child;

    public FieldNode(Node parent, JavaString child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public Option<String> render() {
        return parent.render().map(rendered -> rendered + "." + child);
    }

    @Override
    public boolean is(String name) {
        return name.equals("field");
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some(parent);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new FieldNode(value, child));
    }
}
