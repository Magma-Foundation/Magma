package magma.compile.transform;

import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.result.Result;

public interface TransformingStage {
    Result<Node, CompileError> transform(Node tree, List_<String> namespace);
}
