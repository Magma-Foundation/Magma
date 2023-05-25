package com.meti;

public class Function implements Node {
    private final String name;

    public Function(String name) {
        this.name = name;
    }

    @Override
    public String render() {
        return "class def " + name + "()";
    }
}