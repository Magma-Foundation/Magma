package com.meti.compile.common.variable;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

public class Variable implements Node {
    private final Text value;

    public Variable(Text value) {
        this.value = value;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) new TextAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Variable;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value
                ? new Variable(attribute.asText())
                : this;
    }
}
