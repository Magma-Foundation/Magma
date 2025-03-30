package magma.compile.transform;

import magma.compile.Node;

public interface Transformer {
    Node afterPass(Node node);
}
