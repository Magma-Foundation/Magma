package com.meti.app.compile.primitive;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.stage.Transformer;

public record BooleanResolver(Node node) implements Transformer {
    @Override
    public Option<Node> transform() throws CompileException {
        if (node.is(Node.Type.Primitive) && node.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .computeTrimmed()
                .equals(Primitive.Bool.name())) {
            return new Some<>(new IntegerType(true, 16));
        } else {
            return new None<>();
        }
    }
}
