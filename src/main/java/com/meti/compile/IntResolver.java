package com.meti.compile;

import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

record IntResolver(Node node) implements Transformer {
    @Override
    public Option<Node> transform() {
        if (node.is(Node.Type.Integer)) {
            return new Some<>(new IntegerType(true, 16));
        } else {
            return new None<>();
        }
    }
}
