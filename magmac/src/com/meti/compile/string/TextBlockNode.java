package com.meti.compile.string;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public class TextBlockNode implements Node {
    private final JavaString value;

    public TextBlockNode(JavaString value) {
        this.value = value;
    }

    @Override
    public Option<String> render() {
        return Some.Some("\"\"\"" + value.inner() + "\"\"\"");
    }

    @Override
    public boolean is(String name) {
        return name.equals("text-block");
    }
}
