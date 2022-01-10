package com.meti.compile;

import com.meti.compile.attribute.*;
import com.meti.compile.clang.CFormat;
import com.meti.compile.clang.CRenderer;
import com.meti.compile.common.Field;
import com.meti.compile.common.Import;
import com.meti.compile.common.block.Splitter;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.magma.MagmaLexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;
import com.meti.source.Packaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record MagmaCCompiler(Map<Packaging, String> inputMap) {
    public Map<Packaging, Output<String>> compile() throws CompileException {
        var outputMap = new HashMap<Packaging, Output<String>>();
        for (Packaging aPackaging : inputMap.keySet()) {
            var input = compileInput(aPackaging, inputMap.get(aPackaging));
            outputMap.put(aPackaging, input);
        }
        return outputMap;
    }

    private Output<String> compileInput(Packaging thisPackage, String input) throws CompileException {
        if (input.isBlank()) return new EmptyOutput<>();

        var root = new Text(input);
        var lines = split(root);
        var lexed = lex(lines);
        var resolved = resolve(lexed);
        var formatted = format(thisPackage, resolved);
        var divided = divide(thisPackage, formatted);
        return renderMap(divided);
    }

    private List<Text> split(Text root) {
        return new Splitter(root)
                .split()
                .collect(Collectors.toList());
    }

    private ArrayList<Node> lex(List<Text> lines) throws CompileException {
        var oldNodes = new ArrayList<Node>();
        for (Text oldLine : lines) {
            oldNodes.add(new MagmaLexer(oldLine).lex());
        }
        return oldNodes;
    }

    private ArrayList<Node> resolve(ArrayList<Node> lexed) throws AttributeException {
        var resolved = new ArrayList<Node>();
        for (Node node : lexed) {
            resolved.add(resolve(node));
        }
        return resolved;
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

    private Map<CFormat, List<Node>> divide(Packaging thisPackage, ArrayList<Node> newNodes) {
        var map = new HashMap<CFormat, List<Node>>();
        for (Node node : newNodes) {
            if (node.is(Node.Type.Import) || node.is(Node.Type.Extern) || node.is(Node.Type.Structure)) {
                List<Node> list;
                if (!map.containsKey(CFormat.Header)) {
                    list = new ArrayList<>();
                    var header = thisPackage.formatDeclared();
                    list.add(new Content(new Text("\n#ifndef " + header + "\n")));
                    list.add(new Content(new Text("\n#define " + header + "\n")));
                    map.put(CFormat.Header, list);
                } else {
                    list = map.get(CFormat.Header);
                }
                list.add(node);
            } else {
                List<Node> list;
                if (!map.containsKey(CFormat.Source)) {
                    list = new ArrayList<>();
                    list.add(new Import(new Packaging(thisPackage.computeName())));
                    map.put(CFormat.Source, list);
                } else {
                    list = map.get(CFormat.Source);
                }
                list.add(node);
            }
        }

        if (map.containsKey(CFormat.Header)) {
            map.get(CFormat.Header).add(new Content(new Text("\n#endif\n")));
        }
        return map;
    }

    private Output<String> renderMap(Map<CFormat, List<Node>> map) throws CompileException {
        var output = new MappedOutput<>(map);
        return output.map((format, list) -> {
            var builder = new StringBuilder();
            for (Node line : list) {
                builder.append(new CRenderer(line).render().compute());
            }
            return builder.toString().trim();
        });
    }

    private Node resolve(Node node) throws AttributeException {
        var resolved = toBoolean(node)
                .or(() -> fixImplementation(node))
                .or(() -> fixAbstraction(node))
                .orElse(node);
        var list = resolved.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = resolved;
        for (Attribute.Type type : list) {
            var child = current.apply(type).asNode();
            var resolvedChild = resolve(child);
            current = current.with(type, new NodeAttribute(resolvedChild));
        }
        return current;
    }

    private Option<Node> toBoolean(Node node) throws AttributeException {
        if (node.is(Node.Type.Boolean)) {
            return new Some<>(new IntegerNode(node.apply(Attribute.Type.Value).asBoolean() ? 1 : 0));
        }
        return new None<>();
    }

    private Option<Node> fixImplementation(Node node) throws AttributeException {
        return node.is(Node.Type.Implementation)
                ? new Some<>(fixBooleans(node))
                : new None<>();
    }

    private Option<Node> fixAbstraction(Node node) throws AttributeException {
        if (node.is(Node.Type.Abstraction)) {
            if (node.apply(Attribute.Type.Identity)
                    .asNode()
                    .apply(Attribute.Type.Flags)
                    .asStreamOfFlags()
                    .noneMatch(value -> value == Field.Flag.Extern)) {
                return new Some<>(fixBooleans(node));
            } else {
                return new Some<>(new EmptyNode());
            }
        } else {
            return new None<>();
        }
    }

    private Node fixBooleans(Node node) throws AttributeException {
        var oldParameters = node.apply(Attribute.Type.Parameters)
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
        return node.with(Attribute.Type.Parameters, new NodesAttribute(newParameters));
    }
}
