package com.meti.compile.imports;

import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Node;

public record ImportNode(String parent, String child) implements Node {
    @Override
    public Option<String> getChild() {
        return Some.apply(child);
    }

    @Override
    public Option<String> getParent() {
        return Some.apply(parent);
    }
}