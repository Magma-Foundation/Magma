package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Option;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

public interface Node {
    Option<Node> with(String_ key, Attribute attribute);

    Option<Attribute> apply(String_ key);

    Option<Node> withLines(List<? extends Node> lines);

    Option<Node> withBody(Node body);

    Option<Node> withReturns(Node returns);

    Option<Node> withParameters(Set<? extends Node> parameters);
}
