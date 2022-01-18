package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

record FunctionFormatter(Node node) implements Transformer {
    /*
    Not sure whether this should be static or not yet,
    this could potentially be dangerous...

    - SirMathhman, 1/17/2021
     */
    private static int counter = -1;

    @Override
    public Option<Node> transform() throws CompileException {
        if (node.is(Node.Type.Abstraction) || node.is(Node.Type.Implementation)) {
            var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
            var oldName = oldIdentity.apply(Attribute.Type.Name).asText();
            Text newName;
            if (oldName.isEmpty()) {
                counter++;
                newName = new Text("__lambda" + counter + "__");
            } else {
                newName = oldName;
            }
            var newIdentity = oldIdentity.with(Attribute.Type.Name, new TextAttribute(newName));
            return new Some<>(node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity)));
        } else {
            return new None<>();
        }
    }
}
