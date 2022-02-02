package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.text.Input;

public class InputType extends InputNode implements Type {
    public InputType(Input input) {
        super(input);
    }

    @Override
    public boolean isAssignableTo(Type other) throws AttributeException {
        return other.is(Role.Input);
    }
}
