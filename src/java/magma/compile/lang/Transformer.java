package magma.compile.lang;

import magma.collect.list.List_;
import magma.compile.Node;

public interface Transformer {
    Node transform(Node tree, List_<String> namespace);
}
