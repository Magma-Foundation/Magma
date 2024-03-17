package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Some.Some;

public record ClassNode(JavaList<JavaString> flags, JavaString name,
                        Node value) implements Node {
    @Override
    public Option<Node> findValueAsNode() {
        return Some(value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new ClassNode(flags, name, value));
    }

    @Override
    public Option<String> render() {
        var flagsString = flags.stream()
                .collect(Collectors.joining(new JavaString(" ")))
                .map(value -> value.concatSlice(" "))
                .orElse(JavaString.Empty);

        return Some("\n" + flagsString + "object " + name.inner() + " = " + value.render().orElse(""));
    }

    @Override
    public boolean is(String name) {
        return name.equals("object");
    }
}