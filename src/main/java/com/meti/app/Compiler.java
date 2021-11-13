package com.meti.app;

import com.meti.app.attribute.Attribute;
import com.meti.app.node.Content;
import com.meti.app.stage.CRenderingProcessingStage;
import com.meti.app.stage.MagmaLexingProcessingStage;

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
