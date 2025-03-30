package magma.compile.transform;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.result.Result;

public interface TransformingStage {
    Result<Node, CompileError> transform(Node tree, State state);
}
