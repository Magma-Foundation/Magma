package magmac.app.lang.node;

/**
 * Generic holder for a block of code. The header describes the block (for
 * example an {@code if} condition) and the children are the statements inside
 * the braces: {@code if (cond) { ... }}.
 */

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
