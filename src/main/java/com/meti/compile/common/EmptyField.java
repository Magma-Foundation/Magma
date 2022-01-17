package com.meti.compile.common;

import com.meti.collect.JavaList;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

public class EmptyField extends Field {
    public EmptyField(JavaList<Flag> flags, Text name, Node type) {
        super(flags, name, type);
    }

    @Override
    protected Field complete(Text name, Node type) {
        return new EmptyField(flags, name, type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.EmptyField;
    }
}
