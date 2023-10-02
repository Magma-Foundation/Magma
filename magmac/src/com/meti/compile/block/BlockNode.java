package com.meti.compile.block;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Node;

public record BlockNode(com.meti.api.collect.List<JavaString> lines) implements Node {
    @Override
    public Option<List<JavaString>> getLines() {
        return Some.apply(lines);
    }

    @Override
    public boolean is(String name) {
        return name.equals("block");
    }
}