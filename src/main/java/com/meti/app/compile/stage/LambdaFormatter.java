package com.meti.app.compile.stage;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

class LambdaFormatter implements Processor<Node> {
    private static int counter = -1;
    private final Node node;

    public LambdaFormatter(Node node) {
        this.node = node;
    }

    @Override
    public Option<Node> process() throws CompileException {
        if (node.is(Node.Type.Abstraction) || node.is(Node.Type.Implementation)) {
            var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
            var oldName = oldIdentity.apply(Attribute.Type.Name).asInput();
            Input newName;
            if (oldName.isEmpty()) {
                counter++;
                newName = new RootText("__lambda" + counter + "__");
            } else {
                newName = oldName;
            }
            var newIdentity = oldIdentity.with(Attribute.Type.Name, new InputAttribute(newName));
            return new Some<>(node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity)));
        } else {
            return new None<>();
        }
    }
}
