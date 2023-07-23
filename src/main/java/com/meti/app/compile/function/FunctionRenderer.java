package com.meti.app.compile.function;

import com.meti.app.Attribute;
import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
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
            var joinedParameters = root.parameters().$().iter()
                    .map(node2 -> node2.apply(fromSlice("value")).flatMap(Attribute::asString))
                    .flatMap(Iterators::fromOption)
                    .collect(JavaString.joining(fromSlice(", ")))
                    .unwrapOrElse(JavaString.Empty);

            var renderedKeywords = root.keywords().$().iter()
                    .map(value -> value.append(" "))
                    .collect(JavaString.joiningEmpty())
                    .unwrapOrElse(Empty);

            Node node1 = root.body().$();
            var body = node1.apply(fromSlice("value")).flatMap(Attribute::asString).$();
            var returns = root.returns()
                    .flatMap(node -> node.apply(fromSlice("value")).flatMap(Attribute::asString))
                    .map(value -> JavaString.fromSlice(": ").appendOwned(value).append(" "))
                    .unwrapOrElse(Empty);

            return renderedKeywords.append("def ")
                    .appendOwned(root.name().$()).append("(")
                    .appendOwned(joinedParameters).append(") ")
                    .appendOwned(returns)
                    .append("=> ")
                    .appendOwned(body);
        });
    }
}