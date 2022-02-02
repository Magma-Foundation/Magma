package com.meti.app.compile.primitive;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;

public record BooleanResolver(Node node) implements Processor<Node> {
    public Option<Node> process() throws CompileException {
        if (node.is(Node.Type.Primitive) && node.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .compute()
                .equals(Primitive.Bool.name())) {
            return new Some<>(new IntegerType(true, 16));
        } else {
            return new None<>();
        }
    }
}
