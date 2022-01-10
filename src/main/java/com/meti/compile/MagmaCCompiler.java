package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.PackageAttribute;
import com.meti.compile.clang.CFormat;
import com.meti.compile.clang.CRenderer;
import com.meti.compile.common.block.Splitter;
import com.meti.compile.magma.MagmaLexer;
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
        var formatted = format(thisPackage, lexed);
        var divided = divide(formatted);
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

    private Map<CFormat, List<Node>> divide(ArrayList<Node> newNodes) {
        var map = new HashMap<CFormat, List<Node>>();
        for (Node node : newNodes) {
            if (node.is(Node.Type.Import) || node.is(Node.Type.Extern) || node.is(Node.Type.Structure)) {
                List<Node> list = new ArrayList<>();
                if (!map.containsKey(CFormat.Header)) {
                    map.put(CFormat.Header, list);
                } else {
                    list = map.get(CFormat.Header);
                }
                list.add(node);
            } else {
                List<Node> list = new ArrayList<>();
                if (!map.containsKey(CFormat.Source)) {
                    map.put(CFormat.Source, list);
                } else {
                    list = map.get(CFormat.Source);
                }
                list.add(node);
            }
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
            return builder.toString();
        });
    }
}
