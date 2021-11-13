package com.meti;

import com.meti.node.Content;

public record Compiler(String input) {
    String compile() throws CompileException {
        if (input.isBlank()) return "";
        var root = new MagmaLexingProcessingStage(new Content(input)).process();
        return new CRenderingProcessingStage(root)
                .process()
                .apply(Attribute.Type.Value)
                .asString();
    }
}
