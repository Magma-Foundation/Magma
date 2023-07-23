package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;
import com.meti.java.String_;

public final class ExplicitImplementation extends Abstraction {
    private final Node body1;

    @Override
    public Option<Node> withParameters(Set<? extends Node> parameters) {
        return Some.apply(new ExplicitImplementation(keywords1, name1, parameters, body1, returnType));
    }

    public ExplicitImplementation(Set<String_> keywords1, String_ name1, Set<? extends Node> parameters1, Node body1,
                                  Node returnType) {
        super(keywords1, name1, parameters1, returnType);
        this.body1 = body1;
    }

    @Override
    public Option<Node> withReturns(Node returns) {
        return Some.apply(new ExplicitImplementation(keywords1, name1, parameters1, body1, returns));
    }

    public Option<Node> body() {
        return Some.apply(body1);
    }

    @Override
    public Option<Node> withBody(Node body) {
        return Some.apply(new ExplicitImplementation(keywords1, name1, parameters1, body, returnType));
    }
}