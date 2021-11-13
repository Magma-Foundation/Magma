package com.meti;

class MagmaRenderer {
    private final String name;
    private final String type;

    public MagmaRenderer(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String render() {
        return "def " + name + "() : " + type + " => {return 0;}";
    }
}
