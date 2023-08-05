package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Option;
import com.meti.iterate.Iterator;
import com.meti.java.Key;
import com.meti.java.String_;

public interface Node {
    Iterator<Key<String_>> ofGroup(Group group);

    boolean is(String_ name);

    Node with(Key<String_> key, Attribute attribute);

    Attribute apply(Key<String_> key);

    Option<Attribute> applyOptionally(String_ key);

    Option<Key<String_>> has(String_ key);

    String_ getType();

    enum Group {
        NodeSet,
        NodeList,
        String, StringSet, Node
    }
}
