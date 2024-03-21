package com.meti.compile;

import com.meti.collect.result.Result;
import com.meti.collect.result.ThrowableOption;
import com.meti.collect.stream.Collectors;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.result.Results.$Result;

public record Compiler(String input) {

    private static Result<Node, CompileException> compileLine(LexingStage stage, JavaString line) {
        return $Result(() -> stage.lexExpression(line, 0)
                .$()
                .first()
                .into(ThrowableOption::new)
                .orElseThrow(() -> new CompileException("Failed to lex: '" + line + "'"))
                .$());
    }

    Result<JavaString, CompileException> compile() throws CompileException {
        return $Result(() -> {
            var tree = new Splitter(this.input())
                    .split()
                    .map(JavaString::from)
                    .map(line -> compileLine(new LexingStage(), line))
                    .collect(Collectors.exceptionally(Collectors.toList()))
                    .$();

            var outputTree = tree.stream()
                    .filter(element -> !element.is("package"))
                    .map(TransformingStage::transformAST)
                    .collect(Collectors.exceptionally(Collectors.toList()))
                    .$();

            return outputTree.stream()
                    .map(RenderingStage::renderExpression)
                    .collect(Collectors.exceptionally(Collectors.joining())).mapValue(value -> value.orElse(JavaString.Empty));
        }).$();
    }
}