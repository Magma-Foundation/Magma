package com.meti.app.compile.clazz;

import com.meti.app.compile.Node;
import com.meti.app.compile.Transformable;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.String_;

public record Class_(String_ name1, Node body1) implements Transformable, Node {
    @Override
    public Option<Node> transform() {
        return new ClassTransformer(this).transform();
    }

    @Override
    public Option<String_> name() {
        return Some.apply(name1);
    }

    @Override
    public Option<Node> body() {
        return Some.apply(body1);
    }

    @Override
    public Option<Node> withBody(Node body) {
        return Some.apply(new Class_(name1, body));
    }
}