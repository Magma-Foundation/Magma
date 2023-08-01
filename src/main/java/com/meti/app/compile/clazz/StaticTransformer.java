package com.meti.app.compile.clazz;

import com.meti.app.compile.MapNode;
import com.meti.app.compile.Node;
import com.meti.app.compile.attribute.*;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.core.Some;
import com.meti.iterate.Iterators;
import com.meti.java.JavaList;
import com.meti.java.JavaMap;
import com.meti.java.List;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record StaticTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            if (!root.is(fromSlice("class"))) return Options.$$();

            var name = root.applyOptionally(fromSlice("name")).$().asString().$();
            Attribute attribute = root.applyOptionally(fromSlice("body")).$();
            var body = attribute.asNode().map(value -> value.b()).$();
            Attribute attribute1 = body.applyOptionally(fromSlice("lines")).$();
            var lines = attribute1.asListOfNodes().<List<? extends Node>>map(value -> value.b()).$();
            var newLines = lines.iter()
                    .map(line -> {
                        if (line.is(fromSlice("method"))) {
                            return line.withOptionally(fromSlice("keywords"), new StringSetAttribute());
                        } else {
                            return Some.apply(line);
                        }
                    })
                    .flatMap(Iterators::fromOption)
                    .collect(JavaList.intoList());

            var map = JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("name"), new StringAttribute(name.append("s")))
                    .insert(fromSlice("body"), new NodeAttribute(fromSlice("any"), new MapNode(fromSlice("block"), JavaMap.<String_, Attribute>empty()
                            .insert(fromSlice("lines"), new NodeListAttribute(name, newLines)))));
            return new MapNode(fromSlice("object"), map);
        });
    }
}
