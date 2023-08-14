package com.meti.app.compile.clazz;

import com.meti.app.compile.MapNode;
import com.meti.app.compile.Node;
import com.meti.app.compile.attribute.*;
import com.meti.core.Option;
import com.meti.core.Tuple;
import com.meti.java.*;

import static com.meti.core.Options.$$;
import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record StaticTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            if (!root.is(fromSlice("class"))) return $$();

            var name = root.applyOptionally(fromSlice("name")).$().asString().$();
            var attribute = root.applyOptionally(fromSlice("statements")).$();
            var body = attribute.asNode().map(Tuple::b).$();
            var attribute1 = body.applyOptionally(fromSlice("lines")).$();
            var lines = attribute1.asListOfNodes().<List<? extends Node>>map(Tuple::b).$();
            var finalCache = lines.iter().foldLeft(new Cache(), (cache, element) -> {
                var key1 = element.has(fromSlice("keywords"));
                return key1.map(keywordsKey -> {
                    if (element.is(fromSlice("implementation")) && element.apply(keywordsKey)
                            .asSetOfStrings()
                            .unwrapOrElse(JavaSet.empty())
                            .has(fromSlice("static"))) {

                        return cache.withStatic(element.with(keywordsKey, new StringSetAttribute()));
                    } else {
                        return cache.withInstance(element);
                    }
                }).unwrapOrElse(cache);
            });

            if (finalCache.hasAnyStaticValues()) {
                var map = JavaMap.<String_, Attribute>empty()
                        .insert(fromSlice("name"), new StringAttribute(name.append("s")))
                        .insert(fromSlice("statements"), new NodeAttribute(fromSlice("any"), new MapNode(fromSlice("block"), JavaMap.<String_, Attribute>empty()
                                .insert(fromSlice("lines"), new NodeListAttribute(name, finalCache.staticNodes)))));
                return new MapNode(fromSlice("object"), map);
            } else {
                return $$();
            }
        });
    }

    record Cache(List<Node> staticNodes, List<Node> instanceNodes) {
        Cache() {
            this(JavaList.empty(), JavaList.empty());
        }

        public Cache withStatic(Node node) {
            return new Cache(staticNodes.add(node), instanceNodes);
        }

        public Cache withInstance(Node node) {
            return new Cache(staticNodes, instanceNodes.add(node));
        }

        public boolean hasAnyStaticValues() {
            return !this.staticNodes.isEmpty();
        }
    }
}
