package com.meti;

import java.util.List;

public record BlockNode(List<String> lines) implements Node {
    @Override
    public Option<List<String>> getLines() {
        return Some.apply(lines);
    }
}