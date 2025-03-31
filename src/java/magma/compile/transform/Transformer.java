package magma.compile.transform;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.option.Tuple;
import magma.result.Result;

public interface Transformer {
    Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node);

    Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node);
}
