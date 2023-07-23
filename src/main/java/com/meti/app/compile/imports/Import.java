package com.meti.app.compile.imports;

import com.meti.app.compile.Node;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.List;
import com.meti.java.String_;

public record Import(String_ parent1, String_ child1) implements Node {
    @Override
    public Option<String_> parent() {
        return Some.apply(parent1);
    }

    @Override
    public Option<String_> child() {
        return Some.apply(child1);
    }

    @Override
    public Option<List<? extends Node>> lines() {
        return None.apply();
    }
}