package com.meti.app.compile.primitive;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;

public record IntResolver(Node node) implements Processor<Node> {
    public Option<Node> process() {
        if (node.is(Node.Role.Integer)) {
            return new Some<>(new IntegerType(true, 16));
        } else {
            return new None<>();
        }
    }
}
