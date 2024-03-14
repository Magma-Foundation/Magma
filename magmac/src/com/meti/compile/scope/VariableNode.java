package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Node;

public class VariableNode implements Node  {
    private final String exp;

    public VariableNode(String exp) {
        this.exp = exp;
    }

    @Override
    public Option<String> render() {
        return Some.Some(exp);
    }

    @Override
    public boolean is(String name) {
        return name.equals("variable");
    }
}
