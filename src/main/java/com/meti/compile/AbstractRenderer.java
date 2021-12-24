package com.meti.compile;

public abstract class AbstractRenderer implements Processor<String, RenderException> {
    protected final Node node;

    public AbstractRenderer(Node node) {
        this.node = node;
    }

}
