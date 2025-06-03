package magmac.app.lang.node;

import magmac.api.collect.list.List;

public class Block<H, S> {
    protected final H header;
    protected final List<S> segments;

    public Block(H header, List<S> segments) {
        this.header = header;
        this.segments = segments;
    }

    public H header() {
        return this.header;
    }

    public List<S> segments() {
        return this.segments;
    }
}
