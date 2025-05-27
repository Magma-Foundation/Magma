package magmac.app.compile.node;

import magmac.api.iter.Iter;

public interface NodeList {
    Iter<Node> iter();

    Node last();
}
