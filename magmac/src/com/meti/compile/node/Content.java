package com.meti.compile.node;

import com.meti.collect.option.Option;

import static com.meti.collect.option.Some.Some;

public class Content implements Node {
    private final String value;
    private final int indent;

    @Override
    public Option<String> findValueAsString() {
        return Some(value);
    }

    @Override
    public Option<Integer> findIndent() {
        return Some(indent);
    }

    public Content(String value, int indent) {
        this.value = value;
        this.indent = indent;
    }

    @Override
    public Option<String> render() {
        return Some(value);
    }
}
