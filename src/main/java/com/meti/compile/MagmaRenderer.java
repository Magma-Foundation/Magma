package com.meti.compile;

import java.util.stream.Collectors;

public class MagmaRenderer {
    private final Node root;

    public MagmaRenderer(Node root) {
        this.root = root;
    }

    private static Input renderField(Node node) throws AttributeException, RenderException {
        var name = node.apply(Attribute.Type.Name).asInput();
        var type = node.apply(Attribute.Type.Type).asNode();

        if (type.is(Node.Type.Integer)) {
            var isSigned = type.apply(Attribute.Type.Sign).asBoolean();
            var bits = type.apply(Attribute.Type.Bits).asInteger();
            String suffix;
            if (bits == 16) suffix = "int";
            else throw new RenderException("Unknown bit quantity: " + bits);
            String value = (isSigned ? "" : "unsigned ") + suffix + " " + name.getInput();
            return new Input(value);
        } else {
            throw new RenderException("Cannot render type: " + type);
        }
    }

    static String renderNode(Node node) throws CompileException {
        if (node.is(Node.Type.Function)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var text = identity.apply(Attribute.Type.Value)
                    .asInput()
                    .getInput();
            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asInput().getInput();
            return text + "()" + renderedValue;
        }
        if (node.is(Node.Type.Block)) {
            var builder = new StringBuilder().append("{");
            var children = node.apply(Attribute.Type.Children).streamNodes().collect(Collectors.toList());
            for (Node node1 : children) {
                builder.append(node1.apply(Attribute.Type.Value).asInput().getInput());
            }
            return builder.append("}").toString();
        }
        if (node.is(Node.Type.Return)) {
            var child = node.apply(Attribute.Type.Value).asNode();
            var renderedChild = child.apply(Attribute.Type.Value).asInput().getInput();
            return "return " + renderedChild + ";";
        } else if (node.is(Node.Type.Content)) {
            return node.apply(Attribute.Type.Value).asInput().getInput();
        } else {
            throw new CompileException("Unable to render node:" + node);
        }
    }

    public static Node renderSubFields(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Field).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var node = root.apply(type).asNode();
            var renderedNode = renderField(node);
            current = current.with(type, new NodeAttribute(new Content(renderedNode)));
        }
        return current;
    }

    public static Node renderSubNodes(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var node = root.apply(type).asNode();
            var renderedNode = new MagmaRenderer(node).render();
            current = current.with(type, new NodeAttribute(new Content(renderedNode)));
        }
        return current;
    }

    Input render() throws CompileException {
        var withFields = renderSubFields(root);
        var withNodes = renderSubNodes(withFields);
        return new Input(renderNode(withNodes));
    }
}
