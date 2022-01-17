package com.meti.compile;

import com.meti.collect.CollectionException;
import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.compile.clang.CFormat;
import com.meti.compile.clang.CRenderer;
import com.meti.compile.common.block.Splitter;
import com.meti.compile.magma.MagmaLexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.source.Packaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CMagmaCompiler(JavaMap<Packaging, String> input) {
    public Map<Packaging, Output<String>> compile() throws CompileException {
        return input.mapValues(this::compileInput).getMap();
    }

    private Output<String> compileInput(Packaging thisPackage, String input) throws CompileException {
        if (input.isBlank()) return new EmptyOutput<>();

        var root = new Text(input);
        var lines = split(root);
        var lexed = lex(lines);
        try {
            var resolver = new CMagmaNodeResolver();
            var modifier = new CMagmaModifier();
            var formatter = new CFormatter(thisPackage);
            var divider = new CDivider(thisPackage);

            var division = new JavaList<>(lexed)
                    .stream()
                    .map(resolver::transformNodeAST)
                    .map(modifier::transformNodeAST)
                    .map(formatter::apply)
                    .foldRight(divider, Divider::divide);
            return division.stream().<Output<String>, CollectionException>foldRight(new MappedOutput<>(),
                    (output, format) -> output.append(format, renderDivision(division, format)));
        } catch (CollectionException e) {
            throw new CompileException(e);
        }
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

    private String renderDivision(Divider divider, CFormat format) throws CollectionException {
        return divider.apply(format)
                .map(CRenderer::new)
                .map(CRenderer::render)
                .map(Text::compute)
                .foldRight(new StringBuilder(), StringBuilder::append)
                .toString();
    }

}
