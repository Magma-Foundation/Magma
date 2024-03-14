package com.meti.compile.node;

import com.meti.collect.option.Option;
import com.meti.java.JavaString;

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

    public Content(JavaString value, int indent) {
        this.value = value.inner();
        this.indent = indent;
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
