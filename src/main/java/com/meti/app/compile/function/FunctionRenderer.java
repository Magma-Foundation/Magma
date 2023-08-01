package com.meti.app.compile.function;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.app.compile.attribute.Attribute;
import com.meti.core.*;
import com.meti.iterate.Iterators;
import com.meti.java.JavaString;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;

public record FunctionRenderer(Node root) implements Renderer {
    @Override
    public Option<Result<String_, CompileException>> render() {
        if (root.is(fromSlice("method"))) {
            return Some.apply(renderValid());
        } else {
            return None.apply();
        }
    }

    private Result<String_, CompileException> renderValid() {
        return renderWithinOption()
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElseGet(() -> Err.apply(new CompileException("Invalid function configuration.")));
    }

    private Option<String_> renderWithinOption() {
        return $Option(() -> {
            var joinedParameters = root.applyOptionally(fromSlice("parameters")).flatMap(Attribute::asListOfNodes).$().iter()
                    .map(node2 -> node2.applyOptionally(fromSlice("value")).flatMap(Attribute::asString))
                    .flatMap(Iterators::fromOption)
                    .collect(JavaString.joining(fromSlice(", ")))
                    .unwrapOrElse(Empty);

            var renderedKeywords = root.applyOptionally(fromSlice("keywords")).flatMap(Attribute::asSetOfStrings).$().iter()
                    .map(value -> value.append(" "))
                    .collect(JavaString.joiningEmpty())
                    .unwrapOrElse(Empty);

            var node1 = root.applyOptionally(fromSlice("body")).flatMap(Attribute::asNode).$();
            var body = node1.applyOptionally(fromSlice("value")).flatMap(Attribute::asString).$();
            var returns = root.applyOptionally(fromSlice("returns")).flatMap(Attribute::asNode)
                    .flatMap(node -> node.applyOptionally(fromSlice("value")).flatMap(Attribute::asString))
                    .map(value -> fromSlice(": ").appendOwned(value).append(" "))
                    .unwrapOrElse(Empty);

            return renderedKeywords.append("def ")
                    .appendOwned(root.applyOptionally(fromSlice("name")).flatMap(Attribute::asString).$()).append("(")
                    .appendOwned(joinedParameters).append(") ")
                    .appendOwned(returns)
                    .append("=> ")
                    .appendOwned(body);
        });
    }
}