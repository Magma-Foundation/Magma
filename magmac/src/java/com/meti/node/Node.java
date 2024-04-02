package com.meti.node;

import java.util.Optional;

public interface Node extends Renderable {
    default boolean is(String name) {
        return false;
    }

    @Override
    default String render() {
        if (is("trait")) {
            var prefix = apply("prefix").flatMap(Attribute::asString).orElseThrow();
            var name = apply("name").flatMap(Attribute::asString).orElseThrow();
            var content = apply("content").flatMap(Attribute::asString).orElseThrow();

            return prefix + "trait " + name + " " + content;
        }

        var prefix = apply("prefix").flatMap(Attribute::asString).orElse("");
        var name = apply("name").flatMap(Attribute::asString).orElse("");
        var body = apply("body").flatMap(Attribute::asString).orElse("");
        var parameters = apply("parameters").flatMap(Attribute::asString).orElse("");

        return prefix + "record " + name + "(" + parameters + ")" + body;
    }

    Optional<Attribute> apply(String prefix);
}
