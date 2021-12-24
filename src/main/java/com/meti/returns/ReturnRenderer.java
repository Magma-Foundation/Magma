package com.meti.returns;

import com.meti.AbstractRenderer;
import com.meti.Input;
import com.meti.Node;
import com.meti.RenderException;
import com.meti.attribute.Attribute;
import com.meti.attribute.AttributeException;
import com.meti.option.None;
import com.meti.option.Option;

public class ReturnRenderer extends AbstractRenderer {
    public ReturnRenderer(Node node) {
        super(node);
    }

    @Override
    public Option<String> process() throws RenderException {
        if (node.is(Node.Type.Return)) {
            try {
                return node.getValue().map(Attribute::asNode)
                        .flatMap(node1 -> node1.getValue().map(Attribute::asInput))
                        .map(Input::compute)
                        .map(value -> "return " + value + ";");
            } catch (AttributeException e) {
                throw new RenderException(e);
            }
        } else {
            return new None<>();
        }
    }
}
