package com.meti.node;

import java.util.Optional;

public interface Node extends Renderable {
    @Override
    default String render() {
        var prefix = apply("prefix").flatMap(Attribute::asString).orElse("");
        var name = apply("name").flatMap(Attribute::asString).orElse("");
        var body = apply("body").flatMap(Attribute::asString).orElse("");
        var parameters = apply("parameters").flatMap(Attribute::asString).orElse("");

        return prefix + "record " + name + "(" + parameters + ")" + body;
    }

    Optional<Attribute> apply(String prefix);
}
