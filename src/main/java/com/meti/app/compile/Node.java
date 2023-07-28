package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Option;
import com.meti.iterate.Iterator;
import com.meti.iterate.Iterators;
import com.meti.java.Key;
import com.meti.java.String_;

public interface Node {
    default Iterator<Key<String_>> ofGroup() {
        return Iterators.empty();
    }

    boolean is(String_ name);

    Option<Node> with(String_ key, Attribute attribute);

    Option<Attribute> applyOptionally(String_ key);
}
