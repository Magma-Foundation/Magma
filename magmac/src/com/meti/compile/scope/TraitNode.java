package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Collectors;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public class TraitNode implements Node {
    private final JavaString name;
    private final Node content;
    private final JavaList<JavaString> flags;

    public TraitNode(JavaString name, Node content, JavaList<JavaString> flags) {
        this.name = name;
        this.content = content;
        this.flags = flags;
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some.Some(new TraitNode(name, value, flags));
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some.Some(content);
    }

    @Override
    public Option<String> render() {
        var flagString = flags.stream()
                .collect(Collectors.joining(new JavaString(" ")))
                .map(value -> value + " ")
                .orElse("");

        return Some.Some(flagString + "trait " + name + " " + content.render().orElse(""));
    }

    @Override
    public boolean is(String name) {
        return name.equals("interface");
    }
}
