package com.meti.app.compile.stage;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;
import com.meti.app.compile.text.Input;

public abstract class AbstractStage implements Stage {
    protected Node transformDefinition(Node definition) throws CompileException {
        return definition;
    }

    protected Node beforeNodeTraversal(Node root) throws CompileException {
        return root;
    }

    protected Node transformDeclarationsGroup(Node node1, Attribute.Type type) throws CompileException {
        try {
            return node1.with(type, node1.apply(type).asStreamOfNodes()
                    .map(this::transformDefinition)
                    .foldRight(new NodesAttribute.Builder(), NodesAttribute.Builder::add)
                    .complete());
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformDeclarationsGroup(Node withDeclarationGroup) throws CompileException {
        try {
            return withDeclarationGroup.apply(Attribute.Group.Definitions)
                    .foldRight(withDeclarationGroup, this::transformDeclarationsGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    @Override
    public Node transformNodeAST(Node node) throws CompileException {
        try {
            var before = beforeNodeTraversal(node);
            var withNodeGroup = transformNodeGroup(before);
            var withNodesGroup = transformNodesGroup(withNodeGroup);
            var withDeclarationGroup = transformDefinitionGroup(withNodesGroup);
            var withDeclarationsGroup = transformDeclarationsGroup(withDeclarationGroup);
            return afterTraversal(withDeclarationsGroup);
        } catch (CompileException e) {
            var format = "Failed to transform node:\n-----\n%s\n-----\n";
            var message = format.formatted(node);
            throw new CompileException(message, e);
        }
    }

    protected Node transformDefinitionGroup(Node root, Attribute.Type type) throws CompileException {
        var oldIdentity = root.apply(type).asNode();
        var newIdentity = transformDefinition(oldIdentity);
        var newIdentityAttribute = new NodeAttribute(newIdentity);
        return root.with(type, newIdentityAttribute);
    }

    protected Node transformDefinitionGroup(Node root) throws CompileException {
        try {
            return root.apply(Attribute.Group.Definition)
                    .foldRight(root, this::transformDefinitionGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node afterTraversal(Node root) throws CompileException {
        return root;
    }

    protected Node transformNodeAttribute(Node current, Attribute.Type type) throws CompileException {
        var previousAttribute = current.apply(type);
        var previous = previousAttribute.asNode();
        var next = transformNodeAST(previous);
        var nextAttribute = new NodeAttribute(next);
        return current.with(type, nextAttribute);
    }

    protected Node transformNodeGroup(Node node) throws CompileException {
        try {
            return node.apply(Attribute.Group.Node).foldRight(node, this::transformNodeAttribute);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformNodesAttribute(Node current, Attribute.Type type) throws CompileException {
        try {
            return current.with(type, current.apply(type)
                    .asStreamOfNodes()
                    .map(AbstractStage.this::transformNodeAST)
                    .foldRight(new NodesAttribute.Builder(), NodesAttribute.Builder::add)
                    .complete());
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformNodesGroup(Node withChild) throws CompileException {
        try {
            return withChild.apply(Attribute.Group.Nodes)
                    .foldRight(withChild, this::transformNodesAttribute);
        } catch (StreamException e) {
            throw new TransformationException(e);
        }
    }

    protected Node transformTypeAttribute(Node oldIdentity) throws CompileException {
        var name = oldIdentity.apply(Attribute.Type.Name).asInput();
        var oldType = oldIdentity.apply(Attribute.Type.Type).asNode();

        var newType = transformType(name, oldType);
        var newTypeAttribute = new NodeAttribute(newType);
        return oldIdentity.with(Attribute.Type.Type, newTypeAttribute);
    }

    protected Node transformType(Input name, Node type) throws CompileException {
        return type;
    }
}
