package com.meti.app.compile.clazz;

import com.meti.app.Attribute;
import com.meti.app.compile.MapNode;
import com.meti.app.compile.Node;
import com.meti.app.compile.NodeAttribute;
import com.meti.app.compile.StringAttribute;
import com.meti.app.compile.block.Block;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.java.JavaMap;
import com.meti.java.String_;

import static com.meti.core.Options.$$;
import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record StaticTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            if (!root.is(fromSlice("class"))) return Options.$$();
            var body = root.apply(fromSlice("body")).$().asNode().$();
            var lines = body.apply(fromSlice("lines")).$().asListOfNodes().$();
            if (lines.isEmpty()) {
                return $$();
            } else {
                var map = JavaMap.<String_, Attribute>empty()
                        .insert(fromSlice("name"), new StringAttribute(fromSlice("Tests")))
                        .insert(fromSlice("body"), new NodeAttribute(new Block()));
                return new MapNode(fromSlice("object"), map);
            }
        });
    }
}
