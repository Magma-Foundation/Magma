package com.meti.compile;

import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.RenderException;

public final class CacheRenderer extends AbstractRenderer {
    public CacheRenderer(Node node) {
        super(node);
    }

    @Override
    protected boolean filter(Node value) {
        return value.is(Node.Type.Cache);
    }

    @Override
    protected Text renderImpl(Node node) throws RenderException {
        try {
            var renderedChildren = node.apply(Attribute.Type.Children)
                    .asStreamOfNodes()
                    .map(value -> value.apply(Attribute.Type.Value))
                    .map(Attribute::asText)
                    .map(Text::computeTrimmed)
                    .foldRight("", String::concat);
            var value = renderValue(node);
            return value.prepend(renderedChildren);
        } catch (StreamException | AttributeException e) {
            throw new RenderException(e);
        }
    }

    private Text renderValue(Node node) throws AttributeException {
        return node.apply(Attribute.Type.Value)
                .asNode()
                .apply(Attribute.Type.Value)
                .asText();
    }
}
