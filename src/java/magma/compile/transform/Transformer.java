package magma.compile.transform;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.result.Result;

public interface Transformer {
    Result<Node, CompileError> beforePass(State state, Node node);

    Result<Node, CompileError> afterPass(State state, Node node);
}
