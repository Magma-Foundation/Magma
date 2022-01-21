package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

public class EmptyField extends Field {
    public EmptyField(Input name, Node type, Flag... flags) {
        this(name, type, List.apply(flags));
    }

    public EmptyField(Input name, Node type, List<Flag> flags) {
        super(flags, name, type);
    }

    @Override
    protected Field complete(Input name, Node type) {
        return new EmptyField(name, type, flags);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Declaration;
    }
}
