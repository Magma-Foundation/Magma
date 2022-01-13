package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CacheRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if(node.is(Node.Type.Cache)){
            var oldChildren = node.apply(Attribute.Type.Children).asStreamOfNodes()
                    .foldRight(Stream.<Node>builder(), Stream.Builder::add)
                    .build().collect(Collectors.toList());
            var newChildren = new ArrayList<String>();
            for (Node oldChild : oldChildren) {
                newChildren.add(oldChild.apply(Attribute.Type.Value).asText().computeTrimmed());
            }
            var value = node.apply(Attribute.Type.Value).asNode().apply(Attribute.Type.Value).asText();
            return new Some<>(value.prepend(String.join("", newChildren)));
        } else {
            return new None<>();
        }
    }
}
