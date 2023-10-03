package com.meti.compile.node;

import com.meti.api.collect.JavaString;
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
}
