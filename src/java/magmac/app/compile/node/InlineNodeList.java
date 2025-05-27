package magmac.app.compile.node;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iter;

public final class InlineNodeList implements NodeList {
    private final List<Node> elements;

    public InlineNodeList(List<Node> elements) {
        this.elements = elements;
    }

    public static NodeList empty() {
        return new InlineNodeList(Lists.empty());
    }

    @Override
    public Iter<Node> iter() {
        return this.elements.iter();
    }

    @Override
    public Node last() {
        return this.elements.getLast();
    }

    @Override
    public NodeList add(Node element) {
        return new InlineNodeList(this.elements.add(element));
    }

    @Override
    public NodeList addAll(NodeList others) {
        return others.iter().fold(this, NodeList::add);
    }
}
