package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Option;
import com.meti.iterate.Iterators;
import com.meti.java.JavaString;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;

public record FunctionRenderer(Node root) implements Renderer {
    @Override
    public Option<String_> render() {
        return $Option(() -> {
            var joinedParameters = root.applyOptionally(fromSlice("parameters")).flatMap(Attribute::asSetOfNodes).$().iter()
                    .map(node2 -> node2.applyOptionally(fromSlice("value")).flatMap(Attribute::asString))
                    .flatMap(Iterators::fromOption)
                    .collect(JavaString.joining(fromSlice(", ")))
                    .unwrapOrElse(JavaString.Empty);

            var renderedKeywords = root.applyOptionally(fromSlice("keywords")).flatMap(Attribute::asSetOfStrings).$().iter()
                    .map(value -> value.append(" "))
                    .collect(JavaString.joiningEmpty())
                    .unwrapOrElse(Empty);

            Node node1 = root.applyOptionally(fromSlice("body")).flatMap(Attribute::asNode).$();
            var body = node1.applyOptionally(fromSlice("value")).flatMap(Attribute::asString).$();
            var returns = root.applyOptionally(fromSlice("returns")).flatMap(Attribute::asNode)
                    .flatMap(node -> node.applyOptionally(fromSlice("value")).flatMap(Attribute::asString))
                    .map(value -> JavaString.fromSlice(": ").appendOwned(value).append(" "))
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