package com.meti.app.compile.clazz;

import com.meti.app.Attribute;
import com.meti.app.compile.Node;
import com.meti.app.compile.block.Block;
import com.meti.app.compile.declare.Declaration;
import com.meti.app.compile.function.ImplicitImplementation;
import com.meti.core.Option;
import com.meti.java.*;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ClassTransformer(Node root) implements Transformer {
    private static Cache collectDeclaration(Cache cache, Node node) {
        return Objects.cast(Declaration.class, node)
                .map(value -> cache.withParameters(cache.parameters.add(value)))
                .unwrapOrElseGet(() -> cache.withBody(cache.body.add(node)));
    }

    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            var clazz = Objects.cast(Class_.class, root).$();

            var name = clazz.name().$();
            var body = clazz.apply(fromSlice("body")).flatMap(Attribute::asNode).$();
            var block = Objects.cast(Block.class, body).$();

            var cache = block.values()
                    .iter()
                    .foldLeft(new Cache(), ClassTransformer::collectDeclaration);
            return new ImplicitImplementation(JavaSet.of(fromSlice("class")), name, cache.parameters, new Block(cache.body));
        });
    }

    record Cache(Set<Node> parameters, List<Node> body) {
        Cache() {
            this(JavaSet.empty(), JavaList.empty());
        }

        public Cache withParameters(Set<Node> newParameters) {
            return new Cache(newParameters, body);
        }

        public Cache withBody(List<Node> newBody) {
            return new Cache(parameters, newBody);
        }
    }
}