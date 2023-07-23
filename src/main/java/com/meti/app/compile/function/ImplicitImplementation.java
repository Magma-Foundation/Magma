package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;
import com.meti.java.String_;

public final class ImplicitImplementation extends Function {
    private final Node body1;

    public ImplicitImplementation(Set<String_> keywords1, String_ name1, Set<? extends Node> parameters1, Node body1) {
        super(keywords1, name1, parameters1);
        this.body1 = body1;
    }


    @Override
    public Option<Node> body() {
        return Some.apply(body1);
    }

    @Override
    public Option<Node> withBody(Node body) {
        return Some.apply(new ImplicitImplementation(keywords1, name1, parameters1, body));
    }
}