package com.meti.app.compile.function;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;
import com.meti.java.String_;

public class Function implements Node {
    protected final Set<String_> keywords1;
    protected final String_ name1;
    protected final Set<? extends Node> parameters1;
    protected final Node returnType;

    public Function(Set<String_> keywords1, String_ name1, Set<? extends Node> parameters1, Node returnType) {
        this.keywords1 = keywords1;
        this.name1 = name1;
        this.parameters1 = parameters1;
        this.returnType = returnType;
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
}
