package com.meti;

public class MagmaFunctionRenderer {
    public MagmaFunctionRenderer() {
    }

    String render(Function function) {
        return "def " + function.name() + "() : " + function.type() + " => " + function.body();
    }
}