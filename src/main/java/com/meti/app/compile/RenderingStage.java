package com.meti.app.compile;

import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.String_;

public class RenderingStage extends Stage<Node, String_> {

    @Override
    protected Result<Node, CompileException> before(Node input) {
        return Ok.apply(input);
    }

    @Override
    protected Result<String_, CompileException> after(Node output) {
        return new MagmaRenderer(output)
                .render()
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElse(Err.apply(new CompileException("No output present.")));
    }

    @Override
    protected Result<Node, CompileException> toInput(Node node) {
        return Ok.apply(node);
    }

    @Override
    protected Node fromOutput(String_ value) {
        return new Content(value);
    }
}
