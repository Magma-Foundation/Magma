package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Some.Some;

public record LambdaNode(java.util.List<com.meti.java.JavaString> arguments, Node body) implements Node {
    @Override
    public Option<Node> withValue(Node value) {
        return Some(new LambdaNode(arguments, body));
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some(body);
    }

    @Override
    public Option<String> render() {
        JavaString argumentsString;
        if (arguments.size() == 1) {
            argumentsString = arguments.get(0);
        } else {
            argumentsString = new JavaString(Streams.fromList(arguments)
                    .map(JavaString::inner)
                    .collect(Collectors.joiningNatively(", "))
                    .orElse("()"));
        }

        return Some(argumentsString.inner() + " => " + body.render().orElse("{}"));
    }

    @Override
    public boolean is(String name) {
        return name.equals("lambda");
    }
}
