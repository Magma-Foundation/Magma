package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;
import com.meti.java.String_;

public record StringSetAttribute(Set<String_> values) implements Attribute {
    @Override
    public Option<Set<String_>> asSetOfStrings() {
        return Some.apply(values);
    }
}
