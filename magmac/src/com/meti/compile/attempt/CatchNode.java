package com.meti.compile.attempt;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.List;
import java.util.stream.Collectors;

public class CatchNode implements Node {
    private final List<JavaString> exceptionTypes;
    private final JavaString exceptionName;
    private final Node value;
    private final int indent;

    public CatchNode(List<JavaString> exceptionTypes, JavaString exceptionName, Node value, int indent) {
        this.exceptionTypes = exceptionTypes;
        this.exceptionName = exceptionName;
        this.value = value;
        this.indent = indent;
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some.Some(value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some.Some(new CatchNode(exceptionTypes, exceptionName, value, indent));
    }

    @Override
    public Option<String> render() {
        var exceptionTypeString = exceptionTypes.stream()
                .map(JavaString::inner)
                .collect(Collectors.joining(" | ", "(", " " + exceptionName + " )"));

        return Some.Some("\n" + "\t".repeat(indent) +
                         "catch " + exceptionTypeString + value.render().orElse(""));
    }

    @Override
    public boolean is(String name) {
        return name.equals("catch");
    }
}
