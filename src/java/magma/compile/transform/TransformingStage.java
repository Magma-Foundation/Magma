package magma.compile.transform;

import magma.collect.list.List_;
import magma.compile.Node;

public interface TransformingStage {
    Node transform(Node tree, List_<String> namespace);
}
