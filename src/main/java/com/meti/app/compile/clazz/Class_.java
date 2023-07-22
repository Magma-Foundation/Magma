package com.meti.app.compile.clazz;

import com.meti.app.compile.Node;
import com.meti.app.compile.Transformable;
import com.meti.core.Option;
import com.meti.java.String_;

public record Class_(String_ name, Node body) implements Transformable {
    @Override
    public Option<Node> transform() {
        return new ClassTransformer(this).transform();
    }

}