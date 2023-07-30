package com.meti.app.compile.clazz;

import com.meti.app.Attribute;
import com.meti.app.NodeListAttribute;
import com.meti.app.compile.MapNode;
import com.meti.app.compile.Node;
import com.meti.app.compile.declare.Declaration;
import com.meti.app.compile.function.ImplicitImplementation;
import com.meti.core.Option;
import com.meti.java.*;

import static com.meti.core.Options.$$;
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

            var name = clazz.applyOptionally(fromSlice("name")).flatMap(Attribute::asString).$();
            var body = clazz.applyOptionally(fromSlice("body")).flatMap(Attribute::asNode).$();
            if (!body.is(fromSlice("block"))) {
                return $$();
            }

            var cache = body.applyOptionally(fromSlice("lines"))
                    .flatMap(Attribute::asListOfNodes)
                    .unwrapOrElse(JavaList.empty())
                    .iter()
                    .foldLeft(new Cache(), ClassTransformer::collectDeclaration);
            return new ImplicitImplementation(JavaSet.of(fromSlice("class")), name, cache.parameters, new MapNode(fromSlice("block"), JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("lines"), new NodeListAttribute(cache.body))));
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