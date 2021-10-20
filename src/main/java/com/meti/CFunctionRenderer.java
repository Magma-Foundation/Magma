package com.meti;

public class CFunctionRenderer {
    Output render(Node function) {
        return new NodeOutput(function.identity())
                .concat(new StringOutput("()"))
                .concat(new NodeOutput(function.body()));
    }
}