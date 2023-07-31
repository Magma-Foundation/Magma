package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.JavaSet;
import com.meti.java.Set;
import com.meti.java.String_;

public record StringSetAttribute(Set<String_> values) implements Attribute {
    public StringSetAttribute() {
        this(JavaSet.empty());
    }

    @Override
    public Option<Set<String_>> asSetOfStrings() {
        return Some.apply(values);
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.StringSet;
    }
}
