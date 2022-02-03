package com.meti.app.compile.stage;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.java.List;
import com.meti.api.collect.java.Map;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.clang.CRenderer;
import com.meti.app.compile.common.block.Splitter;
import com.meti.app.compile.magma.lex.MagmaLexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;
import com.meti.app.source.Packaging;

public final class CMagmaCompiler {
    private final Map<Packaging, String> input;
    private final MagmaLexer lexer = new MagmaLexer();

    public CMagmaCompiler(Map<Packaging, String> input) {
        this.input = input;
    }

    public Map<Packaging, Target<String>> compile() throws CompileException {
        return input.mapValues(this::compileInput);
    }

    private Target<String> compileInput(Packaging thisPackage, String input) throws CompileException {
        try {
            if (input.isBlank()) return new EmptyTarget<>();
            var root = new RootText(input);

            var inputNodes = new Splitter(root)
                    .split()
                    .map(InputNode::new)
                    .map(lexer::transformNodeAST)
                    .<List<Node>, RuntimeException>foldRight(List.createList(), List::add);

            var pipeline = new CMagmaPipeline(thisPackage, inputNodes);
            var divider = new CDivider(thisPackage);

            var division = pipeline.pipe().foldRight(divider, Divider::divide);
            return division.stream().<Target<String>, CollectionException>foldRight(new MappedTarget<>(),
                    (output, format) -> output.append(format, renderDivision(division, format)));
        } catch (CollectionException | CompileException e) {
            var format = "Failed to compile input:\n-----\n%s\n-----\n";
            var message = format.formatted(input);
            throw new CompileException(message, e);
        }
    }

    private String renderDivision(Divider divider, CFormat format) throws CollectionException {
        var cRenderer = new CRenderer();
        return divider.apply(format)
                .map(cRenderer::transformNodeAST)
                .map(value -> value.apply(Attribute.Type.Value))
                .map(Attribute::asOutput)
                .map(Output::computeRaw)
                .foldRight(new StringBuilder(), StringBuilder::append)
                .toString();
    }
}
