package magmac.app.compile.node;

import magmac.api.Option;
import magmac.api.iter.Iter;

public interface NodeList {
    Iter<Node> iter();

    Option<Node> findLast();

    NodeList add(Node element);

    NodeList addAll(NodeList others);
}
