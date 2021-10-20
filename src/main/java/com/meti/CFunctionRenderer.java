package com.meti;

public class CFunctionRenderer {
    Output render(Node function) throws CompileException {
        return new NodeOutput(function.apply(Attribute.Group.Identity).asNode())
                .concat(new StringOutput("()"))
                .concat(new NodeOutput(function.apply(Attribute.Group.Body).asNode()));
    }
}