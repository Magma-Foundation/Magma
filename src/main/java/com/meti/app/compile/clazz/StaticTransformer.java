package com.meti.app.compile.clazz;

import com.meti.app.Attribute;
import com.meti.app.compile.*;
import com.meti.app.compile.block.Block;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.core.Some;
import com.meti.iterate.Iterators;
import com.meti.java.JavaList;
import com.meti.java.JavaMap;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record StaticTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            if (!root.is(fromSlice("class"))) return Options.$$();

            var name = root.applyOptionally(fromSlice("name")).$().asString().$();
            var body = root.applyOptionally(fromSlice("body")).$().asNode().$();
            var lines = body.applyOptionally(fromSlice("lines")).$().asListOfNodes().$();
            var newLines = lines.iter()
                    .map(line -> {
                        if (line.is(fromSlice("method"))) {
                            return line.with(fromSlice("keywords"), new StringSetAttribute());
                        } else {
                            return Some.apply(line);
                        }
                    })
                    .flatMap(Iterators::fromOption)
                    .collect(JavaList.asList());

            var map = JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("name"), new StringAttribute(name.append("s")))
                    .insert(fromSlice("body"), new NodeAttribute(new Block(newLines)));
            return new MapNode(fromSlice("object"), map);
        });
    }
}
