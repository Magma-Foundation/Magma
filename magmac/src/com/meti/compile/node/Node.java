package com.meti.compile.node;

import com.meti.collect.Pair;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.magma.MagmaRenderer;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;

public interface Node {
    @Deprecated
    default Option<String> render() {
        return new MagmaRenderer(this).render();
    }

    boolean is(String name);

    default Option<Attribute> apply(String name) {
        return None();
    }

    default Option<Node> with(String name, Attribute attribute) {
        return None();
    }

    default Stream<Pair<JavaString, Attribute>> streamPairs(String type) {
        if (type.equals(NodeAttribute.Type)) {
            return apply("value")
                    .map(attribute -> new Pair<>(JavaString.from("value"), attribute))
                    .into(Streams::fromOption);
        } else if(type.equals(NodeListAttribute.Type)) {
            return apply("children")
                    .map(attribute -> new Pair<>(JavaString.from("children"), attribute))
                    .into(Streams::fromOption);
        } else {
            return Streams.empty();
        }
    }
}
