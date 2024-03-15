package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Node;

public record LambdaNode() implements Node {
    @Override
    public Option<String> render() {
        return Some.Some("() => {}");
    }

    @Override
    public boolean is(String name) {
        return name.equals("lambda");
    }
}
