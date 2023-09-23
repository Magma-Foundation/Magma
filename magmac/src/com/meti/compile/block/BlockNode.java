package com.meti.compile.block;

import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Node;

import java.util.List;

public record BlockNode(List<String> lines) implements Node {
    @Override
    public Option<List<String>> getLines() {
        return Some.apply(lines);
    }
}