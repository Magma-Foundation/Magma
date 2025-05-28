package magmac.app.compile.node;

import magmac.api.Option;
import magmac.api.Some;
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

    private Node last() {
        return this.elements.getLast();
    }

    @Override
    public NodeList add(Node element) {
        return new InlineNodeList(this.elements.add(element));
    }

    @Override
    public NodeList addAll(NodeList others) {
        return others.iter().fold(this, (NodeList nodeList, Node element) -> nodeList.add(element));
    }

    @Override
    public Option<Node> findLast() {
        return new Some<>(this.last());
    }
}
