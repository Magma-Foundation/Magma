package com.meti.app.compile.block;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.List;
import com.meti.java.String_;

public record Block(List<Node> values) implements Node {
    @Override
    public Option<List<Node>> lines() {
        return Some.apply(values);
    }

    @Override
    public String_ render() {
        return new BlockRenderer(this).render().unwrap();
    }
}
