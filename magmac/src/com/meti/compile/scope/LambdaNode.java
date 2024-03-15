package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public record LambdaNode(java.util.List<com.meti.java.JavaString> arguments) implements Node {
    @Override
    public Option<String> render() {
        JavaString argumentsString;
        if(arguments.size() == 1) {
            argumentsString = arguments.get(0);
        } else {
            argumentsString = new JavaString(Streams.fromList(arguments)
                    .map(JavaString::inner)
                    .collect(Collectors.joining(", "))
                    .orElse("()"));
        }

        return Some.Some(argumentsString.inner() + " => {}");
    }

    @Override
    public boolean is(String name) {
        return name.equals("lambda");
    }
}
