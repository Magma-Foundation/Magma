package com.meti.compile.node;

import com.meti.api.Tuple;
import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.compile.attribute.Attribute;

public interface Node {
    default Option<Attribute> apply(JavaString name) {
        return None.apply();
    }

    JavaString toXML();

    boolean is(String name);

    default Option<Attribute> apply(String child) {
        return apply(new JavaString(child));
    }

    default Iterator<Tuple<JavaString, Attribute>> iter() {
        return Iterators.empty();
    }

    default Iterator<Tuple<JavaString, Attribute>> stream(Attribute.Group group) {
        return Iterators.empty();
    }

    default Option<Node> with(JavaString key, Attribute value) {
        return None.apply();
    };
}
