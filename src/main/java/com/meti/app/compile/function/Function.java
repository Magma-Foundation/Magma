package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;
import com.meti.java.String_;

public record Function(Set<String_> keywords, String_ name1,
                       Set<? extends Node> parameters, Node body1, Node returnType) implements Node {
    public String_ render() {
        return new FunctionRenderer(this).render().unwrap();
    }

    @Override
    public Option<Node> withBody(Node body) {
        return Some.apply(new Function(keywords, name1, parameters, body, returnType));
    }
}