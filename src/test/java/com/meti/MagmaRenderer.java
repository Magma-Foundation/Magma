package com.meti;

public class MagmaRenderer {
    private final String name;

    public MagmaRenderer(String name) {
        this.name = name;
    }

    String render() {
        return "def " + getName() + "() : I16 => {return 0;}";
    }

    public String getName() {
        return name;
    }
}
