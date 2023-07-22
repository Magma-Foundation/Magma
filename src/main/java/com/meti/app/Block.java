package com.meti.app;

import com.meti.java.List;
import com.meti.java.String_;

public record Block(List<Node> lines) implements Node {
    @Override
    public String_ render() {
        return new BlockRenderer(this).render();
    }
}
