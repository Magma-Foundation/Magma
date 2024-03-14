package com.meti;

import static com.meti.Some.Some;

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
