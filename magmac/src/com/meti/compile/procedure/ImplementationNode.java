package com.meti.compile.procedure;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.List;

public final class ImplementationNode extends MethodNode {
    private final Node content;

    public ImplementationNode(int indent, Option<?> moreOutputValue, List<String> annotations, JavaString name, String type,
                              Node content) {
        super(indent, moreOutputValue, annotations, name, type);
        this.content = content;
    }

    @Override
    protected String renderContent() {
        return " => " + content.render().orElse("");
    }

    @Override
    public boolean is(String name) {
        return name.equals("implementation");
    }

    public int indent() {
        return indent;
    }
}