package magmac.app.compile.node;

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
}
