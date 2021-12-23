package com.meti;

public abstract class AbstractRenderer implements Renderer {
    protected final Node node;

    public AbstractRenderer(Node node) {
        this.node = node;
    }

}
