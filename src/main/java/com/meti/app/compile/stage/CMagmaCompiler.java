package com.meti.app.compile.stage;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.java.JavaMap;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.clang.CRenderer;
import com.meti.app.compile.common.block.Splitter;
import com.meti.app.compile.magma.MagmaLexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;
import com.meti.app.source.Packaging;

import java.util.Map;

public record CMagmaCompiler(JavaMap<Packaging, String> input) {
    public Map<Packaging, Target<String>> compile() throws CompileException {
        return input.mapValues(this::compileInput).getMap();
    }

    private Target<String> compileInput(Packaging thisPackage, String input) throws CompileException {
        try {
            if (input.isBlank()) return new EmptyTarget<>();
            var root = new RootText(input);
            var inputNodes = lex(root);
            var pipeline = new CMagmaPipeline(thisPackage, inputNodes);
            var divider = new CDivider(thisPackage);
            var division = pipeline.apply()
                    .foldRight(divider, Divider::divide);
            return division.stream().<Target<String>, CollectionException>foldRight(new MappedTarget<>(),
                    (output, format) -> output.append(format, renderDivision(division, format)));
        } catch (CollectionException e) {
            throw new CompileException(e);
        }
    }

    private List<Node> lex(Input root) throws StreamException {
        var lexer = new MagmaLexer();
        return new Splitter(root)
                .split()
                .map(InputNode::new)
                .map(lexer::transformNodeAST)
                .foldRight(List.createList(), List::add);
    }

    private String renderDivision(Divider divider, CFormat format) throws CollectionException {
        return divider.apply(format)
                .map(CRenderer::new)
                .map(CRenderer::render)
                .map(Output::computeRaw)
                .foldRight(new StringBuilder(), StringBuilder::append)
                .toString();
    }

}
