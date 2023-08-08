package com.meti.app.compile.clazz;

import com.meti.app.compile.MapNode;
import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.java.JavaList;
import com.meti.java.JavaSet;
import com.meti.java.List;

import static com.meti.app.compile.MapNode.Builder;
import static com.meti.app.compile.MapNode.create;
import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ClassTransformer(Node root) implements Transformer {
    private static Cache collectDeclaration(Cache cache, Node node) {
        return node.is(fromSlice("declaration"))
                ? cache.withParameters(cache.parameters.add(node))
                : cache.withBody(cache.statements.add(node));
    }

    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            var extractor = new Extractor();
            var nameKey = extractor.extract("name");
            var linesKey = extractor.extract("lines");

            var node = create("class")
                    .with(nameKey)
                    .withNode("statements", "node", create("block").with(linesKey))
                    .complete();

            var extracted = root.extract(node).$();
            var name = extracted.apply(nameKey.key()).asString().$();
            var lines = extracted.apply(linesKey.key()).asListOfNodes().$();

            var cache = lines.b().iter()
                    .foldLeft(new Cache(), ClassTransformer::collectDeclaration);

            var keywords1 = JavaSet.of(fromSlice("class"));
            var builder = create("implementation")
                    .withSetOfStrings("keywords", keywords1)
                    .withString("name", name);

            return cache.attachTo(builder).complete();
        });
    }

    record Cache(List<Node> parameters, List<Node> statements) {
        Cache() {
            this(JavaList.empty(), JavaList.empty());
        }

        public Cache withParameters(List<Node> newParameters) {
            return new Cache(newParameters, statements);
        }

        public Cache withBody(List<Node> newBody) {
            return new Cache(parameters, newBody);
        }

        public Builder attachTo(Builder builder) {
            return builder.withListOfNodes("parameters", "parameter", parameters)
                    .withNode("statements", "block",
                            MapNode.create("block").withListOfNodes("lines", "statement", statements));
        }
    }
}