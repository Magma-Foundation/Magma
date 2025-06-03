package magmac.app.compile.node;

import magmac.api.iter.collect.Collector;

public class NodeListCollector implements Collector<Node, NodeList> {
    @Override
    public NodeList createInitial() {
        return InlineNodeList.empty();
    }

    @Override
    public NodeList fold(NodeList current, Node element) {
        return current.add(element);
    }
}
