package com.meti.compile;

import com.meti.compile.attribute.*;
import com.meti.compile.clang.CFormat;
import com.meti.compile.clang.CRenderer;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Import;
import com.meti.compile.common.Line;
import com.meti.compile.common.block.Splitter;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.magma.MagmaLexer;
import com.meti.compile.node.*;
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
    public Map<Packaging, Output<String>> compile() throws CompileException {
        return input.mapValues(this::compileInput).getMap();
    }

    private Output<String> compileInput(Packaging thisPackage, String input) throws CompileException {
        if (input.isBlank()) return new EmptyOutput<>();

        var root = new Text(input);
        var lines = split(root);
        var lexed = lex(lines);
        var resolved = resolveStage(lexed);
        var formatted = format(thisPackage, resolved);
        var divided = divide(thisPackage, formatted);
        return renderMap(divided);
    }

    private JavaMap<CFormat, JavaList> divide(Packaging thisPackage, ArrayList<Node> newNodes) {
        var map = new JavaMap<CFormat, JavaList>();
        for (Node node : newNodes) {
            if (node.is(Node.Type.Import) || node.is(Node.Type.Extern) || node.is(Node.Type.Structure)) {
                map = generateHeader(thisPackage, map, node);
            } else {
                map = generateSource(thisPackage, map, node);
            }
        }

        return map;
    }

    private Option<Node> fixAbstraction(Node node) throws AttributeException, ResolutionException {
        if (node.is(Node.Type.Abstraction)) {
            if (node.apply(Attribute.Type.Identity)
                    .asNode()
                    .apply(Attribute.Type.Flags)
                    .asStreamOfFlags()
                    .noneMatch(value -> value == EmptyField.Flag.Extern)) {
                return new Some<>(fixFunction(node));
            } else {
                return new Some<>(new EmptyNode());
            }
        } else {
            return new None<>();
        }
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

    private Option<Node> fixImplementation(Node node) throws AttributeException, ResolutionException {
        return node.is(Node.Type.Implementation)
                ? new Some<>(fixFunction(node))
                : new None<>();
    }

    private ArrayList<Node> format(Packaging thisPackage, ArrayList<Node> oldNodes) throws AttributeException {
        var newNodes = new ArrayList<Node>();
        for (Node node : oldNodes) {
            if (node.is(Node.Type.Import)) {
                var thatPackage = node.apply(Attribute.Type.Value).asPackaging();
                var newPackage = thisPackage.relativize(thatPackage);
                var withPackage = node.with(Attribute.Type.Value, new PackageAttribute(newPackage));
                newNodes.add(withPackage);
            } else {
                newNodes.add(node);
            }
        }
        return newNodes;
    }

    private JavaList generate(Packaging thisPackage) {
        var header = thisPackage.formatDeclared();
        List<Node> list = new ArrayList<>();
        list.add(new Content(new Text("\n#ifndef " + header + "\n")));
        list.add(new Content(new Text("\n#define " + header + "\n")));
        list.add(new Content(new Text("\n#endif\n")));
        return new JavaList(list);
    }

    private JavaMap<CFormat, JavaList> generateHeader(Packaging thisPackage, JavaMap<CFormat, JavaList> javaMap, Node node) {
        return javaMap.ensure(CFormat.Header, () -> generate(thisPackage))
                .mapValue(CFormat.Header, value -> value.insert(value.size() - 1, node));
    }

    private JavaMap<CFormat, JavaList> generateSource(Packaging thisPackage, JavaMap<CFormat, JavaList> javaMap, Node node) {
        return javaMap.ensure(CFormat.Source, () -> {
            var list = new ArrayList<Node>();
            list.add(new Import(new Packaging(thisPackage.computeName())));
            list.add(node);
            return new JavaList(list);
        }).mapValue(CFormat.Source, list -> list.add(node));
    }

    private ArrayList<Node> lex(List<Text> lines) throws CompileException {
        var oldNodes = new ArrayList<Node>();
        for (Text oldLine : lines) {
            oldNodes.add(new MagmaLexer(oldLine).lex());
        }
        return oldNodes;
    }

    private Output<String> renderMap(JavaMap<CFormat, JavaList> map) throws CompileException {
        var output = new MappedOutput<>(map);
        return output.map((format, list) -> {
            var builder = new StringBuilder();
            for (Node line : list.getValue()) {
                builder.append(new CRenderer(line).render().compute());
            }
            return builder.toString().trim();
        });
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
            if (children.isEmpty()) {
                return Primitive.Void;
            } else {
                for (int i = children.size() - 1; i >= 0; i--) {
                    var child = children.get(i);
                    if (child.is(Node.Type.Return)) {
                        return resolveNode(child);
                    }
                }
                return Primitive.Void;
            }
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
