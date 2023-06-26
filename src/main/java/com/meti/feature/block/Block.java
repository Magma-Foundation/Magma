package com.meti.feature.block;

import com.meti.feature.Node;
import com.meti.safe.SafeList;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record Block(SafeList<? extends Node> lines1) implements Node {
    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    @Override
    public Option<SafeList<? extends Node>> lines() {
        return Some.apply(lines1);
    }

    @Override
    public Node withLines(SafeList<? extends Node> lines) {
        return new Block(lines);
    }

    enum Key {
        Id
    }
}