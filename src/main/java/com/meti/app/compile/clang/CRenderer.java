package com.meti.app.compile.clang;

import com.meti.api.collect.java.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.LineProcessor;
import com.meti.app.compile.common.alternate.ElseProcessor;
import com.meti.app.compile.common.binary.BinaryProcessor;
import com.meti.app.compile.common.block.BlockProcessor;
import com.meti.app.compile.common.condition.ConditionProcessor;
import com.meti.app.compile.common.integer.IntegerProcessor;
import com.meti.app.compile.common.invoke.InvocationProcessor;
import com.meti.app.compile.common.returns.ReturnProcessor;
import com.meti.app.compile.common.string.StringProcessor;
import com.meti.app.compile.common.variable.VariableProcessor;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.OutputNode;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.render.EmptyProcessor;
import com.meti.app.compile.render.RenderException;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record CRenderer(Node root) {
    private static Output renderField(Node node) throws CompileException {
        var name = node.apply(Attribute.Type.Name).asInput();
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
                    .appendOutput(name.toOutput())
                    .appendSlice(")")
                    .appendSlice("(")
                    .appendSlice(String.join(",", newParameters))
                    .appendSlice(")");
        } else if (type.is(Node.Type.Reference)) {
            var child = type.apply(Attribute.Type.Value).asNode();
            return renderFieldWithType(new EmptyField(name.toOutput().prepend("*"), child, List.createList()));
        } else if (type.is(Node.Type.Primitive)) {
            var rendered = type.apply(Attribute.Type.Name)
                    .asInput()
                    .toOutput()
                    .computeTrimmed()
                    .toLowerCase();
            return name.toOutput().prepend(rendered + " ");
        } else if (type.is(Node.Type.Integer)) {
            var isSigned = type.apply(Attribute.Type.Sign).asBoolean();
            var bits = type.apply(Attribute.Type.Bits).asInteger();
            var suffix = switch (bits) {
                case 8 -> "char";
                case 16 -> "int";
                default -> throw new RenderException("Unknown bit quantity: " + bits);
            };
            var value = (isSigned ? "" : "unsigned ") + suffix + " " + name
                    .toOutput()
                    .computeTrimmed();
            return new RootText(value);
        } else {
            throw new RenderException("Cannot render type: " + type);
        }
    }

    private static Output renderFieldWithType(Node node) throws CompileException {
        var common = renderField(node);
        if (node.is(Node.Type.Declaration)) {
            return common;
        } else {
            var value = node.apply(Attribute.Type.Value).asNode();
            var valueText = renderNode(value);
            return common.appendSlice("=").appendOutput(valueText);
        }
    }

    static Output renderNode(Node node) throws CompileException {
        if (node.is(Node.Type.Input)) {
            return node.apply(Attribute.Type.Value)
                    .asInput()
                    .toOutput();
        }

        var renderers = java.util.List.of(
                new BinaryProcessor(node),
                new BlockProcessor(node),
                new ConditionProcessor(node),
                new DeclarationProcessor(node),
                new ElseProcessor(node),
                new EmptyProcessor(node),
                new ExternProcessor(node),
                new FunctionProcessor(node),
                new ImportProcessor(node),
                new IntegerProcessor(node),
                new InvocationProcessor(node),
                new LineProcessor(node),
                new ReturnProcessor(node),
                new StringProcessor(node),
                new StructureProcessor(node),
                new UnaryProcessor(node),
                new VariableProcessor(node));

        Option<Output> current = new None<>();
        for (Processor<Output> renderer : renderers) {
            var result = renderer.process();
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
            current = current.with(type, new NodeAttribute(new OutputNode(renderedNode)));
        }
        return current;
    }

    public static Node renderSubNodes(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var node = root.apply(type).asNode();
            var renderedNode = new CRenderer(node).render();
            current = current.with(type, new NodeAttribute(new OutputNode(renderedNode)));
        }
        return current;
    }

    private static Output renderType(Node oldParameter) throws CompileException {
        return renderField(new EmptyField(new RootText(""), oldParameter, List.createList()));
    }

    public Output render() throws CompileException {
        return renderAST(root);
    }

    private Output renderAST(Node root) throws CompileException {
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
                newNodes.add(new OutputNode(renderAST(oldNode)));
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
                newDeclarations.add(new OutputNode(renderFieldWithType(oldDeclaration)));
            }
            current = current.with(type, new NodesAttribute(newDeclarations));
        }
        return current;
    }
}
