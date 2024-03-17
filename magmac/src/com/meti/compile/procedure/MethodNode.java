package com.meti.compile.procedure;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.collect.option.Some.Some;

public class MethodNode implements Node {
    protected final int indent;
    protected final Option<?> moreOutputValue;
    protected final List<String> annotations;
    protected final JavaString name;
    protected final String type;

    public MethodNode(int indent, Option<?> moreOutputValue, List<String> annotations, JavaString name, String type) {
        this.indent = indent;
        this.moreOutputValue = moreOutputValue;
        this.annotations = annotations;
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean is(String name) {
        return name.equals("method");
    }

    @Override
    public Option<String> render() {
        var exceptions = moreOutputValue.map(value -> " ? " + value).orElse("");
        var annotationsString = annotations.stream().map(annotation -> "\t".repeat(indent) + "@" + annotation + "\n").collect(Collectors.joining());

        return Some("\n" + annotationsString + "\t".repeat(indent) + "def " + name + "() : " + type + exceptions + renderContent());
    }

    protected String renderContent() {
        return "";
    }
}
