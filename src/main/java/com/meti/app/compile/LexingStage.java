package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.core.Tuple;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public final class LexingStage extends Stage<Tuple<String_, String_>, Node> {
    @Override
    protected Result<Node, CompileException> before(Tuple<String_, String_> input) {
        if (input.b().isEmpty()) {
            return Err.apply(new CompileException("Input cannot be empty."));
        }

        return new JavaLexer(input.a(), input.b())
                .lex()
                .unwrapOrElse(Err.apply(new CompileException("Invalid input.")));
    }

    @Override
    protected Result<Node, CompileException> after(Node output) {
        return Ok.apply(output);
    }

    @Override
    protected Result<Tuple<String_, String_>, CompileException> toInput(Node node) {
        var type = node.getType();
        return node.applyOptionally(fromSlice("value"))
                .flatMap(Attribute::asString)
                .map(value -> new Tuple<>(type, value))
                .map(Ok::<Tuple<String_, String_>, CompileException>apply)
                .unwrapOrElseGet(() -> Err.apply(new CompileException("No value present on content.")));
    }

    @Override
    protected Node fromOutput(Node value) {
        return value;
    }
}