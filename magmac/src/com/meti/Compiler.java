package com.meti;

import com.meti.node.Content;
import com.meti.stage.JavaLexingStage;
import com.meti.stage.RenderingStage;
import com.meti.stage.TransformStage;

import java.util.ArrayList;

public class Compiler {
    static String compile(String input) throws CompileException {
        var output = new ArrayList<String>();
        for (var line : new Splitter(input).split()) {
            output.add(compileLine(line.strip()));
        }

        return String.join("\n", output);
    }

    private static String compileLine(String input) throws CompileException {
        if (input.isEmpty()) {
            return "";
        }

        if (input.startsWith("package ")) {
            return "";
        }

        var tree = new JavaLexingStage()
                .apply(new Content("top", input, 0))
                .orElseThrow();

        var transformed = new TransformStage().apply(tree).orElseThrow();

        return new RenderingStage().apply(transformed)
                .orElseThrow(() -> new CompileException("Cannot render: " + tree))
                .strip();
    }
}
