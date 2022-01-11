package com.meti.compile.common.condition;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ConditionRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.If)) {
            var arguments = node.apply(Attribute.Type.Arguments).asNode()
                    .apply(Attribute.Type.Value).asText();
            var value = this.node.apply(Attribute.Type.Value).asNode()
                    .apply(Attribute.Type.Value).asText();
            return new Some<>(new Text("if(" + arguments.compute() + ")" + value.compute()));
        }
        return new None<>();
    }
}
