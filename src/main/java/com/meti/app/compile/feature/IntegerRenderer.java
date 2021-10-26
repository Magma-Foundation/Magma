package com.meti.app.compile.feature;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

public class IntegerRenderer extends AbstractRenderer {
    public IntegerRenderer(Node node) {
        super(node, Node.Type.Integer);
    }

    @Override
    protected Output processDefined() {
        Option<Attribute> result;
        try {
            result = new Some<>(node.apply(Attribute.Type.Value));
        } catch (AttributeException e) {
            result = new None<>();
        }
        var value = result
                .map(Attribute::asInteger)
                .orElse(-1);

        return new StringOutput(String.valueOf(value));
    }
}
