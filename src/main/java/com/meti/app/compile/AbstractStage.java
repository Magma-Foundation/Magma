package com.meti.app.compile;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute1;
import com.meti.app.compile.node.Node;

public abstract class AbstractStage implements Stage {
    protected Node transformDeclarationsGroup(Node node1, Attribute.Type type) throws CompileException {
        try {
            return node1.with(type, node1.apply(type).asStreamOfNodes1()
                    .map(this::transformDefinition)
                    .foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add)
                    .complete());
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformDeclarationsGroup(Node withDeclarationGroup) throws CompileException {
        try {
            return withDeclarationGroup.apply1(Attribute.Group.Declarations)
                    .foldRight(withDeclarationGroup, this::transformDeclarationsGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Node transformDefinition(Node definition) throws CompileException;

    protected Node transformDefinitionGroup(Node root, Attribute.Type type) throws CompileException {
        var oldIdentity = root.apply(type).asNode();
        var newIdentity = transformDefinition(oldIdentity);
        var newIdentityAttribute = new NodeAttribute(newIdentity);
        return root.with(type, newIdentityAttribute);
    }

    protected Node transformDefinitionGroup(Node root) throws CompileException {
        try {
            return root.apply1(Attribute.Group.Definition)
                    .foldRight(root, this::transformDefinitionGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    public Node transformNodeAST(Node node) throws CompileException {
        var withNodeGroup = transformNodeGroup(node);
        var withNodesGroup = transformNodesGroup(withNodeGroup);
        var withDeclarationGroup = transformDefinitionGroup(withNodesGroup);
        var withDeclarationsGroup = transformDeclarationsGroup(withDeclarationGroup);
        return apply(withDeclarationsGroup);
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
            return node.apply1(Attribute.Group.Node).foldRight(node, this::transformNodeAttribute);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformNodesAttribute(Node current, Attribute.Type type) throws CompileException {
        try {
            return current.with(type, current.apply(type)
                    .asStreamOfNodes1()
                    .map(AbstractStage.this::transformNodeAST)
                    .foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add)
                    .complete());
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformNodesGroup(Node withChild) throws CompileException {
        try {
            return withChild.apply1(Attribute.Group.Nodes)
                    .foldRight(withChild, this::transformNodesAttribute);
        } catch (StreamException e) {
            throw new TransformationException(e);
        }
    }

    protected Node transformTypeAttribute(Node oldIdentity) throws CompileException {
        var oldType = oldIdentity.apply(Attribute.Type.Type).asNode();
        var newType = transformType(oldType);
        var newTypeAttribute = new NodeAttribute(newType);
        return oldIdentity.with(Attribute.Type.Type, newTypeAttribute);
    }

    protected Node transformType(Node type) throws CompileException {
        return type;
    }
}
