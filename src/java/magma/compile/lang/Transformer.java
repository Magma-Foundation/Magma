package magma.compile.lang;

import magma.compile.Node;

public interface Transformer {
    Node afterPass(Node node);
}
