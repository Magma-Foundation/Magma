package com.meti.compile.attempt;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.List;
import java.util.stream.Collectors;

public class CatchNode implements Node {
    private final List<JavaString> exceptionTypes;
    private final JavaString exceptionName;
    private final Node value;

    public CatchNode(List<JavaString> exceptionTypes, JavaString exceptionName, Node value) {
        this.exceptionTypes = exceptionTypes;
        this.exceptionName = exceptionName;
        this.value = value;
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some.Some(value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some.Some(new CatchNode(exceptionTypes, exceptionName, value));
    }

    @Override
    public Option<String> render() {
        var exceptionTypeString = exceptionTypes.stream()
                .map(JavaString::inner)
                .collect(Collectors.joining(" | ", "(", " " + exceptionName + " )"));

        return Some.Some("catch " + exceptionTypeString + value.render());
    }

    @Override
    public boolean is(String name) {
        return name.equals("catch");
    }
}
