package com.meti.app.compile.declare;

import com.meti.app.compile.Node;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

public record Declaration(String_ name1, Node type1) implements Node {
    @Override
    public Option<String_> name() {
        return Some.apply(name1);
    }

    @Override
    public Option<Node> type() {
        return Some.apply(type1);
    }

    @Override
    public Option<List<? extends Node>> lines() {
        return None.apply();
    }

    @Override
    public Option<String_> value() {
        return None.apply();
    }

    @Override
    public Option<Node> body() {
        return None.apply();
    }

    @Override
    public Option<Set<? extends Node>> parameters() {
        return None.apply();
    }
}
