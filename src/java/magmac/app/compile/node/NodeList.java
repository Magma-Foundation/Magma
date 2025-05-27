package magmac.app.compile.node;

import magmac.api.iter.Iter;

import java.util.List;

public interface NodeList {
    List<Node> unwrap();

    Iter<Node> iter();
}
