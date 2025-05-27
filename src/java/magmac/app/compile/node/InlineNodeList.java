package magmac.app.compile.node;

import magmac.api.iter.Iter;
import magmac.api.iter.Iters;

import java.util.List;

public final class InlineNodeList implements NodeList {
    private final List<Node> elements;

    public InlineNodeList(List<Node> elements) {
        this.elements = elements;
    }

    @Override
    public List<Node> unwrap() {
        return this.elements;
    }

    @Override
    public Iter<Node> iter() {
        return Iters.fromList(this.elements);
    }
}
