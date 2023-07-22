package com.meti.app.compile.clazz;

import com.meti.app.compile.Content;
import com.meti.app.compile.Declaration;
import com.meti.app.compile.Node;
import com.meti.app.compile.block.Block;
import com.meti.app.compile.function.Function;
import com.meti.core.Option;
import com.meti.java.*;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ClassTransformer(Node clazz) implements Transformer {
    private static Cache collectDeclaration(Cache cache, Node node) {
        return Objects.cast(Declaration.class, node)
                .map(value -> cache.withParameters(cache.parameters.add(value)))
                .unwrapOrElseGet(() -> cache.withBody(cache.body.add(node)));
    }

    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            var name = clazz.name().$();
            var body = clazz.body().$();
            var block = Objects.cast(Block.class, body).$();

            var cache = block.values()
                    .iter()
                    .foldLeft(new Cache(), ClassTransformer::collectDeclaration);
            return new Function(JavaSet.of(fromSlice("class")), name, cache.parameters, new Block(cache.body), new Content(fromSlice("Void")));
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