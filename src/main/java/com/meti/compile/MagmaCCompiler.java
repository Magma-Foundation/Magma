package com.meti.compile;

import com.meti.collect.CollectionException;
import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.clang.CFormat;
import com.meti.compile.clang.CRenderer;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Line;
import com.meti.compile.common.block.Splitter;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.magma.MagmaLexer;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;
import com.meti.source.Packaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record MagmaCCompiler(JavaMap<Packaging, String> input) {
    private static boolean isExternal(Node node) throws AttributeException {
        return node.apply(Attribute.Type.Identity)
                .asNode()
                .apply(Attribute.Type.Flags)
                .asStreamOfFlags()
                .noneMatch(value -> value == EmptyField.Flag.Extern);
    }

    public Map<Packaging, Output<String>> compile() throws CompileException {
        return input.mapValues(this::compileInput).getMap();
    }

    private Output<String> compileInput(Packaging thisPackage, String input) throws CompileException {
        if (input.isBlank()) return new EmptyOutput<>();

        var root = new Text(input);
        var lines = split(root);
        var lexed = lex(lines);
        var resolved = resolveStage(lexed);

        try {
            var formatter = new CFormatter(thisPackage);
            var divider = new CDivider(thisPackage);

            var division = new JavaList<>(resolved)
                    .stream()
                    .map(formatter::apply)
                    .foldRight(divider, Divider::divide);
            return division.stream().<Output<String>, CollectionException>foldRight(new MappedOutput<>(), (output, format) -> output.append(format, renderDivision(division, format)));
        } catch (CollectionException e) {
            throw new CompileException(e);
        }
    }

    private Option<Node> fixAbstraction(Node node) throws CompileException {
        return new Some<>(node)
                .filter(value -> value.is(Node.Type.Abstraction))
                .map(value -> isExternal(value) ? fixFunction(node) : new EmptyNode());
    }

    private Option<Node> fixBlock(Node node) throws AttributeException {
        if (node.is(Node.Type.Block)) {
            var oldChildren = node.apply(Attribute.Type.Children)
                    .asStreamOfNodes()
                    .collect(Collectors.toList());
            var newChildren = new ArrayList<Node>();
            for (Node oldChild : oldChildren) {
                Node newChild;
                if (oldChild.is(Node.Type.Abstraction) || oldChild.is(Node.Type.Implementation)) {
                    newChild = new Cache(oldChild, Collections.emptyList());
                } else if (oldChild.is(Node.Type.Invocation) || oldChild.is(Node.Type.Binary)) {
                    newChild = new Line(oldChild);
                } else {
                    newChild = oldChild;
                }
                newChildren.add(newChild);
            }
            return new Some<>(node.with(Attribute.Type.Children, new NodesAttribute(newChildren)));
        } else {
            return new None<>();
        }
    }

    private Node fixFunction(Node node) throws AttributeException, ResolutionException {
        var identity = node.apply(Attribute.Type.Identity).asNode();
        var oldReturnType = identity.apply(Attribute.Type.Type).asNode();
        Node newReturnType;
        if (oldReturnType.is(Node.Type.Implicit)) {
            var value = node.apply(Attribute.Type.Value).asNode();
            newReturnType = resolveNode(value);
        } else {
            newReturnType = oldReturnType;
        }
        var newIdentity = identity.with(Attribute.Type.Type, new NodeAttribute(newReturnType));
        var withIdentity = node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));

        var oldParameters = withIdentity.apply(Attribute.Type.Parameters)
                .asStreamOfNodes()
                .collect(Collectors.toList());
        var newParameters = new ArrayList<Node>();
        for (Node parameter : oldParameters) {
            var oldType = parameter.apply(Attribute.Type.Type).asNode();
            if (oldType.is(Node.Type.Primitive) && oldType.apply(Attribute.Type.Name).asText().compute().equals("Bool")) {
                var newType = new IntegerType(true, 16);
                newParameters.add(parameter.with(Attribute.Type.Type, new NodeAttribute(newType)));
            } else {
                newParameters.add(parameter);
            }
        }
        return withIdentity.with(Attribute.Type.Parameters, new NodesAttribute(newParameters));
    }

    private Option<Node> fixImplementation(Node node) throws CompileException {
        return new Some<>(node)
                .filter(value -> value.is(Node.Type.Implementation))
                .map(this::fixFunction);
    }

    private ArrayList<Node> lex(List<Text> lines) throws CompileException {
        var oldNodes = new ArrayList<Node>();
        for (Text oldLine : lines) {
            oldNodes.add(new MagmaLexer(oldLine).lex());
        }
        return oldNodes;
    }

    private String renderDivision(Divider divider, CFormat format) throws CollectionException {
        return divider.apply(format)
                .map(CRenderer::new)
                .map(CRenderer::render)
                .map(Text::compute)
                .foldRight(new StringBuilder(), StringBuilder::append)
                .toString();
    }

    private Node resolveField(Node field) throws CompileException {
        if (field.is(Node.Type.ValuedField)) {
            var oldValue = field.apply(Attribute.Type.Value).asNode();
            var newValue = resolveStage(oldValue);
            return field.with(Attribute.Type.Value, new NodeAttribute(newValue));
        } else {
            return field;
        }
    }

    private Node resolveFieldStage(Node root) throws CompileException {
        var current = root;
        var declarations = current.apply(Attribute.Group.Declaration).collect(Collectors.toList());
        for (Attribute.Type type : declarations) {
            var oldField = current.apply(type).asNode();
            var newField = resolveField(oldField);
            current = current.with(type, new NodeAttribute(newField));
        }
        return current;
    }

    private Node resolveNode(Node root) throws AttributeException, ResolutionException {
        if (root.is(Node.Type.Block)) {
            var children = root.apply(Attribute.Type.Children)
                    .asStreamOfNodes()
                    .collect(Collectors.toList());
            if (!children.isEmpty()) {
                for (int i = children.size() - 1; i >= 0; i--) {
                    var child = children.get(i);
                    if (child.is(Node.Type.Return)) {
                        return resolveNode(child);
                    }
                }
            }
            return Primitive.Void;
        } else if (root.is(Node.Type.Return)) {
            var value = root.apply(Attribute.Type.Value).asNode();
            return resolveNode(value);
        } else if (root.is(Node.Type.Integer)) {
            return new IntegerType(true, 16);
        } else {
            throw new ResolutionException("Cannot resolve: " + root);
        }
    }

    private Node resolveNodeAttributes(Node root) throws CompileException {
        var list = root.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = root;
        var cached = new ArrayList<Node>();

        for (Attribute.Type type : list) {
            var oldChild = current.apply(type).asNode();
            var newChild = resolveStage(oldChild);

            if (newChild.is(Node.Type.Cache)) {
                var internalChild = newChild.apply(Attribute.Type.Value).asNode();
                cached.addAll(newChild.apply(Attribute.Type.Children).asStreamOfNodes().collect(Collectors.toList()));
                current = current.with(type, new NodeAttribute(internalChild));
            } else {
                current = current.with(type, new NodeAttribute(newChild));
            }
        }

        if (cached.isEmpty()) {
            return current;
        } else {
            return new Cache(current, cached);
        }
    }

    private Node resolveNodesAttributes(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = root;
        var cached = new ArrayList<Node>();

        for (Attribute.Type type : types) {
            var children = current.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newChildren = new ArrayList<Node>();
            for (Node oldChild : children) {
                var newChild = resolveStage(oldChild);

                if (newChild.is(Node.Type.Cache)) {
                    var internalChild = newChild.apply(Attribute.Type.Value).asNode();
                    if (internalChild.is(Node.Type.Implementation)) {
                        cached.add(internalChild);
                    }
                } else {
                    newChildren.add(newChild);
                }
            }
            current = current.with(type, new NodesAttribute(newChildren));
        }

        if (cached.isEmpty()) {
            return current;
        } else {
            return new Cache(current, cached);
        }
    }

    private ArrayList<Node> resolveStage(ArrayList<Node> lexed) throws CompileException {
        var resolved = new ArrayList<Node>();
        for (Node node : lexed) {
            resolved.add(resolveStage(node));
        }
        return resolved;
    }

    private Node resolveStage(Node node) throws CompileException {
        var resolved = toBoolean(node)
                .or(() -> fixImplementation(node))
                .or(() -> fixAbstraction(node))
                .or(() -> fixBlock(node))
                .orElse(node);
        var withNodes = resolveNodeAttributes(resolved);
        var withNodesAttributes = resolveNodesAttributes(withNodes);
        return resolveFieldStage(withNodesAttributes);
    }

    private List<Text> split(Text root) {
        return new Splitter(root)
                .split()
                .collect(Collectors.toList());
    }

    private Option<Node> toBoolean(Node node) throws AttributeException {
        if (node.is(Node.Type.Boolean)) {
            return new Some<>(new IntegerNode(node.apply(Attribute.Type.Value).asBoolean() ? 1 : 0));
        }
        return new None<>();
    }

}
