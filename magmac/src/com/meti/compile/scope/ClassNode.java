package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.compile.node.*;
import com.meti.java.JavaString;

import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record ClassNode(JavaList<JavaString> flags, JavaString name,
                        Node value) implements Node {


    private Option<Node> findValueAsNode() {
        return Some(value);
    }

    private Option<Node> withValue(Node value) {
        return Some(new ClassNode(flags, name, value));
    }

    @Override
    public boolean is(String name) {
        return name.equals("object");
    }

    @Override
    public Option<Node> with(String name, Attribute attribute) {
        return switch (name) {
            case "children" -> attribute.asListOfNodes()
                    .map(JavaList::unwrap)
                    .flatMap(children -> {
                        return None();
                    });
            case "value" -> attribute.asNode().flatMap(this::withValue);
            default -> None();
        };
    }

    @Override
    public Option<Attribute> apply(String name) {
        return switch (name) {
            case "children" -> None.<List<? extends Node>>None()
                    .map(JavaList::new)
                    .map(NodeListAttribute::new);
            case "value" -> findValueAsNode()
                    .<Attribute>map(NodeAttribute::new)
                    .orLazy(() -> None.<String>None()
                            .map(JavaString::new)
                            .map(StringAttribute::new));
            case "flags" -> Some(flags).map(StringListAttribute::new);
            case "name" -> Some(this.name).map(StringAttribute::new);
            case "indent" -> None.<Integer>None().map(IntegerAttribute::new);
            default -> None();
        };
    }
}