package magma.compile.transform;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.option.Tuple;
import magma.result.Result;

public interface TransformingStage {
    Result<Tuple<State, Node>, CompileError> transform(State state, Node root);
}
