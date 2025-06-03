package magmac.app.lang.node;

import magmac.api.collect.list.List;

public class Block<H, S> {
    protected final H header;
    protected final List<S> children;

    public Block(H header, List<S> children) {
        this.header = header;
        this.children = children;
    }

    public H header() {
        return this.header;
    }

    public List<S> segments() {
        return this.children;
    }
}
