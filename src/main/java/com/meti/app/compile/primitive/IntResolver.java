package com.meti.app.compile.primitive;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.Transformer;

public record IntResolver(Node node) implements Transformer {
    @Override
    public Option<Node> transform() {
        if (node.is(Node.Type.Integer)) {
            return new Some<>(new IntegerType(true, 16));
        } else {
            return new None<>();
        }
    }
}
