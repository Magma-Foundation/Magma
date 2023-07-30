package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.String_;

public record StringAttribute(String_ value) implements Attribute {
    @Override
    public Option<String_> asString() {
        return Some.apply(value);
    }

    @Override
    public boolean is(Node.Group group) {
        throw new UnsupportedOperationException();
    }
}
