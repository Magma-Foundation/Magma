package com.meti;

public class Implementation extends Function {
    private final Node body;

    public Implementation(String name, Block body) {
        this(name, false, body);
    }

    public Implementation(String name, boolean isPublic, Node body) {
        super(name, isPublic);
        this.body = body;
    }

    @Override
    public String render() {
        return super.render() + body.render();
    }
}
