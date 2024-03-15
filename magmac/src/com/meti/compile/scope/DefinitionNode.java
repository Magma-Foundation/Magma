package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.List;

import static com.meti.collect.option.Some.Some;

public record DefinitionNode(int indent, List<JavaString> flags, com.meti.java.JavaString name,
                             Node value) implements Node {
    @Override
    public Option<String> render() {
        var withPrefix = Streams.fromList(flags).map(JavaString::inner)
                .collect(Collectors.joiningNatively(" "))
                .map(value -> value + " ")
                .orElse("");

        return Some(withPrefix + name.inner() + " = " + value.render().orElse(""));
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some(value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new DefinitionNode(indent, flags, name, value));
    }

    @Override
    public boolean is(String name) {
        return name.equals("definition");
    }
}