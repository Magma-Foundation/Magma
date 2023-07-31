package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public final class LexingStage extends Stage<String_, Node> {
    @Override
    protected Result<Node, CompileException> before(String_ input) {
        if (input.isEmpty()) {
            return Err.apply(new CompileException("Input cannot be empty."));
        }

        return new JavaLexer(input).lex().unwrapOrElse(Ok.apply(new Content(input)));
    }

    @Override
    protected Result<Node, CompileException> after(Node output) {
        return Ok.apply(output);
    }

    @Override
    protected Result<String_, CompileException> toInput(Node node) {
        return node.applyOptionally(fromSlice("value"))
                .flatMap(Attribute::asString)
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElseGet(() -> Err.apply(new CompileException("No value present on content.")));
    }

    @Override
    protected Node fromOutput(Node value) {
        return value;
    }
}