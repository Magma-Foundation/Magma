package com.meti.compile.common;

import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.Set;

public class EmptyField extends Field {
    public EmptyField(Set<Flag> flags, Text name, Node type) {
        super(flags, name, type);
    }

    @Override
    protected Field complete(Node type) {
        return new EmptyField(flags, name, type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.EmptyField;
    }
}
