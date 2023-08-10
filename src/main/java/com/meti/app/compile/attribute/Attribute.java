package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Tuple;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

public interface Attribute {
    boolean is(Node.Group group);

    default Option<Set<String_>> asSetOfStrings() {
        return None.apply();
    }

    default Option<Tuple<String_, List<? extends Node>>> asListOfNodes() {
        return None.apply();
    }

    default Option<Tuple<String_, Node>> asNode() {
        return None.apply();
    }

    default Option<String_> asString() {
        return None.apply();
    }

    boolean equalsTo(Attribute other);
}
