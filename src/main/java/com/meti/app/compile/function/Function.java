package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;
import com.meti.java.String_;

public record Function(Set<String_> keywords1, String_ name1,
                       Set<? extends Node> parameters1, Node body1, Node returnType) implements Node {
    public String_ render() {
        return new FunctionRenderer(this).render().unwrap();
    }

    @Override
    public Option<Set<String_>> keywords() {
        return Some.apply(keywords1);
    }


    @Override
    public Option<String_> name() {
        return Some.apply(name1);
    }

    @Override
    public Option<Set<? extends Node>> parameters() {
        return Some.apply(parameters1);
    }

    @Override
    public Option<Node> returns() {
        return Some.apply(returnType);
    }

    @Override
    public Option<Node> body() {
        return Some.apply(body1);
    }

    @Override
    public Option<Node> withBody(Node body) {
        return Some.apply(new Function(keywords1, name1, parameters1, body, returnType));
    }
}