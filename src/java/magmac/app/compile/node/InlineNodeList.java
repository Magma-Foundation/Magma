package magmac.app.compile.node;

import magmac.api.iter.Iter;
import magmac.api.iter.Iters;

import java.util.ArrayList;
import java.util.List;

public final class InlineNodeList implements NodeList {
    private final List<Node> elements;

    public InlineNodeList(List<Node> elements) {
        this.elements = elements;
    }

    public static NodeList empty() {
        return new InlineNodeList(new ArrayList<>());
    }

    @Override
    public Iter<Node> iter() {
        return Iters.fromList(this.elements);
    }

    @Override
    public Node last() {
        return this.elements.getLast();
    }
}
