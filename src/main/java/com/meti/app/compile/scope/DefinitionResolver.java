package com.meti.app.compile.scope;

import com.meti.app.compile.magma.MagmaTypeResolver;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.process.AbstractProcessor;
import com.meti.app.compile.stage.CompileException;

public class DefinitionResolver extends AbstractProcessor<Node, Node> {
    private final Node node;

    public DefinitionResolver(Node node) {
        super(node);
        this.node = node;
    }

    @Override
    protected boolean validate() {
        return node.is(Node.Role.Declaration);
    }

    @Override
    protected Node createNode() throws CompileException {
        var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
        var type = oldIdentity.apply(Attribute.Type.Type).asNode();
        if (type.is(Node.Role.Implicit)) {
            var value = oldIdentity.apply(Attribute.Type.Value).asNode();
            var valueType = new MagmaTypeResolver().transformNodeAST(value);
            var newIdentity = oldIdentity.with(Attribute.Type.Type, new NodeAttribute(valueType));
            return node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));
        }
        return node;
    }
}
