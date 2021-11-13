package com.meti;

public record CRenderer(String name) {
    public String render() {
        return "int " + name + "(){return 0;}";
    }
}
