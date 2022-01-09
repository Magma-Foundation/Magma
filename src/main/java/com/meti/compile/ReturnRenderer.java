package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ReturnRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws AttributeException {
        if (node.is(Node.Type.Return)) {
            var child = node.apply(Attribute.Type.Value).asNode();
            var renderedChild = child.apply(Attribute.Type.Value).asInput().compute();
            return new Some<>(new Text("return ").append(renderedChild).append(";"));
        }
        return new None<>();
    }
}
