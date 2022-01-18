package com.meti.app.compile;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.java.List;
import com.meti.api.collect.java.JavaMap;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.clang.CRenderer;
import com.meti.app.compile.common.block.Splitter;
import com.meti.app.compile.magma.MagmaLexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.RootText;
import com.meti.app.compile.node.Text;
import com.meti.app.source.Packaging;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public record CMagmaCompiler(JavaMap<Packaging, String> input) {
    public Map<Packaging, Output<String>> compile() throws CompileException {
        return input.mapValues(this::compileInput).getMap();
    }

    private Output<String> compileInput(Packaging thisPackage, String input) throws CompileException {
        if (input.isBlank()) return new EmptyOutput<>();

        var root = new RootText(input);
        var lines = split(root);
        var lexed = lex(lines);
        try {
            var pipeline = new CMagmaPipeline(thisPackage);
            var divider = new CDivider(thisPackage);
            var division = List.createList(lexed)
                    .stream()
                    .map(pipeline::perform)
                    .foldRight(divider, Divider::divide);
            return division.stream().<Output<String>, CollectionException>foldRight(new MappedOutput<>(),
                    (output, format) -> output.append(format, renderDivision(division, format)));
        } catch (CollectionException e) {
            throw new CompileException(e);
        }
    }

    private java.util.List<Text> split(RootText root) {
        return new Splitter(root)
                .split()
                .collect(Collectors.toList());
    }

    private ArrayList<Node> lex(java.util.List<Text> lines) throws CompileException {
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

}
