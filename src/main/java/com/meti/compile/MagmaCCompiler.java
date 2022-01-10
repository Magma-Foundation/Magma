package com.meti.compile;

import com.meti.compile.attribute.*;
import com.meti.compile.clang.CFormat;
import com.meti.compile.clang.CRenderer;
import com.meti.compile.common.Import;
import com.meti.compile.common.block.Splitter;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.magma.MagmaLexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
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
            if (node.is(Node.Type.Boolean)) {
                resolved.add(new IntegerNode(node.apply(Attribute.Type.Value).asBoolean() ? 1 : 0));
            } else if (node.is(Node.Type.Function)) {
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
                resolved.add(node.with(Attribute.Type.Parameters, new NodesAttribute(newParameters)));
            } else {
                resolved.add(node);
            }
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
}
