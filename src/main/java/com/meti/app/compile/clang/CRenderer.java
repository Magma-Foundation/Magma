package com.meti.app.compile.clang;

import com.meti.api.collect.java.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.LineRenderer;
import com.meti.app.compile.common.alternate.ElseRenderer;
import com.meti.app.compile.common.binary.BinaryRenderer;
import com.meti.app.compile.common.block.BlockRenderer;
import com.meti.app.compile.common.condition.ConditionRenderer;
import com.meti.app.compile.common.integer.IntegerRenderer;
import com.meti.app.compile.common.invoke.InvocationRenderer;
import com.meti.app.compile.common.returns.ReturnRenderer;
import com.meti.app.compile.common.string.StringRenderer;
import com.meti.app.compile.common.variable.VariableRenderer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.RootText;
import com.meti.app.compile.text.Text;
import com.meti.app.compile.render.EmptyRenderer;
import com.meti.app.compile.render.RenderException;
import com.meti.app.compile.render.Renderer;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record CRenderer(Node root) {
    private static Text renderField(Node node) throws CompileException {
        var name = node.apply(Attribute.Type.Name).asText();
        var type = node.apply(Attribute.Type.Type).asNode();

        if (type.is(Node.Type.Function)) {
            var returns = type.apply(Attribute.Type.Type).asNode();
            var returnType = renderType(returns);
            var oldParameters = type.apply(Attribute.Type.Parameters).asStreamOfNodes().collect(Collectors.toList());
            var newParameters = new ArrayList<String>();
            for (Node oldParameter : oldParameters) {
                var renderedParameter = renderType(oldParameter);
                newParameters.add(renderedParameter.computeTrimmed());
            }
            return returnType.appendSlice(" (*")
                    .appendText(name)
                    .appendSlice(")")
                    .appendSlice("(")
                    .appendSlice(String.join(",", newParameters))
                    .appendSlice(")");
        } else if (type.is(Node.Type.Reference)) {
            var child = type.apply(Attribute.Type.Value).asNode();
            return renderFieldWithType(new EmptyField(name.prepend("*"), child, List.createList()));
        } else if (type.is(Node.Type.Primitive)) {
            var rendered = type.apply(Attribute.Type.Name)
                    .asText().computeTrimmed()
                    .toLowerCase();
            return name.prepend(rendered + " ");
        } else if (type.is(Node.Type.Integer)) {
            var isSigned = type.apply(Attribute.Type.Sign).asBoolean();
            var bits = type.apply(Attribute.Type.Bits).asInteger();
            var suffix = switch (bits) {
                case 8 -> "char";
                case 16 -> "int";
                default -> throw new RenderException("Unknown bit quantity: " + bits);
            };
            var value = (isSigned ? "" : "unsigned ") + suffix + " " + name.computeTrimmed();
            return new RootText(value);
        } else {
            throw new RenderException("Cannot render type: " + type);
        }
    }

    private static Text renderFieldWithType(Node node) throws CompileException {
        var common = renderField(node);
        if (node.is(Node.Type.Declaration)) {
            return common;
        } else {
            var value = node.apply(Attribute.Type.Value).asNode();
            var valueText = renderNode(value);
            return common.appendSlice("=").appendText(valueText);
        }
    }

    static Text renderNode(Node node) throws CompileException {
        if (node.is(Node.Type.Content)) {
            return node.apply(Attribute.Type.Value).asText();
        }

        var renderers = java.util.List.of(
                new BinaryRenderer(node),
                new BlockRenderer(node),
                new ConditionRenderer(node),
                new DeclarationRenderer(node),
                new ElseRenderer(node),
                new EmptyRenderer(node),
                new ExternRenderer(node),
                new FunctionRenderer(node),
                new ImportRenderer(node),
                new IntegerRenderer(node),
                new InvocationRenderer(node),
                new LineRenderer(node),
                new ReturnRenderer(node),
                new StringRenderer(node),
                new StructureRenderer(node),
                new UnaryRenderer(node),
                new VariableRenderer(node));

        Option<Text> current = new None<>();
        for (Renderer renderer : renderers) {
            var result = renderer.render();
            if (result.isPresent()) {
                current = result;
            }
        }

        return current.orElseThrow(() -> new CompileException("Unable to render oldNode: " + node));
    }

    public static Node renderSubFields(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Definition).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var node = root.apply(type).asNode();
            var renderedNode = renderFieldWithType(node);
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

    private static Text renderType(Node oldParameter) throws CompileException {
        return renderField(new EmptyField(new RootText(""), oldParameter, List.createList()));
    }

    public Text render() throws CompileException {
        return renderAST(root);
    }

    private Text renderAST(Node root) throws CompileException {
        try {
            var withFields = renderSubFields(root);
            var withNodes = renderSubNodes(withFields);
            var withNodeCollections = renderSubNodeCollections(withNodes);
            var current = withDeclarationCollections(withNodeCollections);
            return renderNode(current);
        } catch (CompileException e) {
            throw new CompileException("Failed to render AST of oldNode: " + root, e);
        }
    }

    private Node renderSubNodeCollections(Node withNodes) throws CompileException {
        var types = withNodes.apply(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = withNodes;
        for (Attribute.Type type : types) {
            var oldNodes = withNodes.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newNodes = new ArrayList<Node>();
            for (Node oldNode : oldNodes) {
                newNodes.add(new Content(renderAST(oldNode)));
            }
            current = current.with(type, new NodesAttribute(newNodes));
        }
        return current;
    }

    private Node withDeclarationCollections(Node withNodeCollections) throws CompileException {
        var types = withNodeCollections.apply(Attribute.Group.Declarations).collect(Collectors.toList());
        var current = withNodeCollections;
        for (Attribute.Type type : types) {
            var oldDeclarations = current.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newDeclarations = new ArrayList<Node>();
            for (Node oldDeclaration : oldDeclarations) {
                newDeclarations.add(new Content(renderFieldWithType(oldDeclaration)));
            }
            current = current.with(type, new NodesAttribute(newDeclarations));
        }
        return current;
    }
}
