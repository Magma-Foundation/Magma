package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

public interface Node {
    Option<Attribute> apply(String_ key);

    default Option<Node> withLines(List<? extends Node> lines) {
        return None.apply();
    }

    default Option<Node> withBody(Node body) {
        return None.apply();
    }

    default Option<Node> withReturns(Node returns) {
        return None.apply();
    }

    Option<Set<String_>> keywords();

    Option<String_> parent();

    Option<String_> child();

    Option<Node> returns();

    default Option<Node> withParameters(Set<? extends Node> parameters) {
        return None.apply();
    }
}
