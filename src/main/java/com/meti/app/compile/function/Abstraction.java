package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;
import com.meti.java.String_;

public class Abstraction extends Function {
    protected final Node returnType;

    public Abstraction(Set<String_> keywords1, String_ name1, Set<? extends Node> parameters1, Node returnType) {
        super(keywords1, name1, parameters1);
        this.returnType = returnType;
    }

    public Option<Node> withParameters(Set<? extends Node> parameters) {
        return Some.apply(new Abstraction(keywords1, name1, parameters, returnType));
    }

    public Option<Node> withReturns(Node returns) {
        return Some.apply(new Abstraction(keywords1, name1, parameters1, returns));
    }

    public Option<Node> returns() {
        return Some.apply(returnType);
    }
}
