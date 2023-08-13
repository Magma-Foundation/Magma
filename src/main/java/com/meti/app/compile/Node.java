package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.transform.Extractor;
import com.meti.core.Option;
import com.meti.core.Tuple;
import com.meti.iterate.Iterator;
import com.meti.java.Key;
import com.meti.java.Map;
import com.meti.java.String_;

public interface Node {
    Iterator<Key<String_>> ofGroup(Group group);

    default Option<Map<String_, Attribute>> extract(Node format) {
        return new Extractor(this, format).extract();
    }


    Iterator<Key<String_>> keys();

    boolean is(String_ name);

    Node with(Key<String_> key, Attribute attribute);

    Attribute apply(Key<String_> key);

    Option<Attribute> applyOptionally(String_ key);

    Option<Key<String_>> has(String_ key);

    String_ getType();

    boolean equalsTo(Node other);

    Iterator<Tuple<Key<String_>, Attribute>> entries();

    enum Group {
        NodeSet,
        NodeList,
        String, StringSet, Extract, Node
    }
}
