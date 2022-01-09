package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.ListNodeAttribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Content;
import com.meti.compile.node.Text;
import com.meti.compile.node.Node;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record CRenderer(Node root) {
    private static Text renderField(Node node) throws AttributeException, RenderException {
        var name = node.apply(Attribute.Type.Name).asInput();
        var type = node.apply(Attribute.Type.Type).asNode();

        if (type.is(Node.Type.Integer)) {
            var isSigned = type.apply(Attribute.Type.Sign).asBoolean();
            var bits = type.apply(Attribute.Type.Bits).asInteger();
            String suffix;
            if (bits == 16) suffix = "int";
            else throw new RenderException("Unknown bit quantity: " + bits);
            String value = (isSigned ? "" : "unsigned ") + suffix + " " + name.compute();
            return new Text(value);
        } else {
            throw new RenderException("Cannot render type: " + type);
        }
    }

    static String renderNode(Node node) throws CompileException {
        if (node.is(Node.Type.Function)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var text = identity.apply(Attribute.Type.Value)
                    .asInput()
                    .compute();
            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asInput().compute();
            return text + "()" + renderedValue;
        }
        if (node.is(Node.Type.Block)) {
            var builder = new StringBuilder().append("{");
            var children = node.apply(Attribute.Type.Children).asStreamOfNodes().collect(Collectors.toList());
            for (Node node1 : children) {
                builder.append(node1.apply(Attribute.Type.Value).asInput().compute());
            }
            return builder.append("}").toString();
        }
        if (node.is(Node.Type.Return)) {
            var child = node.apply(Attribute.Type.Value).asNode();
            var renderedChild = child.apply(Attribute.Type.Value).asInput().compute();
            return "return " + renderedChild + ";";
        } else if (node.is(Node.Type.Content)) {
            return node.apply(Attribute.Type.Value).asInput().compute();
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
            var renderedNode = new CRenderer(node).render();
            current = current.with(type, new NodeAttribute(new Content(renderedNode)));
        }
        return current;
    }

    Text render() throws CompileException {
        return renderAST(root);
    }

    private Text renderAST(Node root) throws CompileException {
        var withFields = renderSubFields(root);
        var withNodes = renderSubNodes(withFields);
        var types = withNodes.apply(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = withNodes;
        for (Attribute.Type type : types) {
            var oldNodes = withNodes.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newNodes = new ArrayList<Node>();
            for (Node oldNode : oldNodes) {
                newNodes.add(new Content(renderAST(oldNode)));
            }
            current = current.with(type, new ListNodeAttribute(newNodes));
        }
        return new Text(renderNode(current));
    }
}
