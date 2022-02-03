package com.meti.app.compile.stage;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;

public abstract class AbstractStage implements Stage {
    protected Node afterTraversal(Node root) throws CompileException {
        return root;
    }

    protected Node beforeNodeTraversal(Node root) throws CompileException {
        return root;
    }

    protected Node transformDeclarationsGroup(Node node1, Attribute.Category category) throws CompileException {
        try {
            return node1.with(category, node1.apply(category).asStreamOfNodes()
                    .map(this::transformDefinition)
                    .foldRight(new NodesAttribute.Builder(), NodesAttribute.Builder::add)
                    .complete());
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformDefinitionsGroup(Node withDeclarationGroup) throws CompileException {
        try {
            return withDeclarationGroup.apply(Attribute.Group.Definitions)
                    .foldRight(withDeclarationGroup, this::transformDeclarationsGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformDefinitionGroup(Node root) throws CompileException {
        try {
            return root.apply(Attribute.Group.Definition)
                    .foldRight(root, this::transformDefinitionGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformDefinitionGroup(Node root, Attribute.Category category) throws CompileException {
        var oldIdentity = root.apply(category).asNode();
        var newIdentity = transformDefinition(oldIdentity);
        var newIdentityAttribute = new NodeAttribute(newIdentity);
        return root.with(category, newIdentityAttribute);
    }

    protected Node transformDefinition(Node definition) throws CompileException {
        return definition;
    }

    @Override
    public Node transformNodeAST(Node node) throws CompileException {
        try {
            var before = beforeNodeTraversal(node);
            var withNodeGroup = transformNodeGroup(before);
            var withNodesGroup = transformNodesGroup(withNodeGroup);
            var withDeclarationGroup = transformDefinitionGroup(withNodesGroup);
            var withDeclarationsGroup = transformDefinitionsGroup(withDeclarationGroup);
            return afterTraversal(withDeclarationsGroup);
        } catch (CompileException e) {
            var format = "Failed to transform node:\n-----\n%s\n-----\n";
            var message = format.formatted(node);
            throw new CompileException(message, e);
        }
    }

    protected Node transformNodeAttribute(Node current, Attribute.Category category) throws CompileException {
        var previousAttribute = current.apply(category);
        var previous = previousAttribute.asNode();
        var next = transformNodeAST(previous);
        var nextAttribute = new NodeAttribute(next);
        return current.with(category, nextAttribute);
    }

    protected Node transformNodeGroup(Node node) throws CompileException {
        try {
            return node.apply(Attribute.Group.Node).foldRight(node, this::transformNodeAttribute);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected Node transformNodesAttribute(Node current, Attribute.Category category) throws CompileException {
        try {
            return current.with(category, current.apply(category)
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

    protected Node transformType(Node identity) throws CompileException {
        return identity;
    }
}
