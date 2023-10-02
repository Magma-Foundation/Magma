package com.meti.compile.imports;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Node;

public record ImportNode(JavaString parent, JavaString child) implements Node {
    @Override
    public Option<JavaString> getChild() {
        return Some.apply(child);
    }

    @Override
    public Option<JavaString> getParent() {
        return Some.apply(parent);
    }

    @Override
    public boolean is(String name) {
        return name.equals("import");
    }
}