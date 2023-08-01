package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.String_;

public record StringAttribute(String_ value) implements Attribute {
    @Override
    public Option<String_> asString() {
        return Some.apply(value);
    }

    @Override
    public String toString() {
        return "'" + value.toString() + "'";
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.String;
    }
}
