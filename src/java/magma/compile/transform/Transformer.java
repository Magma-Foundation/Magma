package magma.compile.transform;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public interface Transformer {
    default Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        return new Ok<>(new Tuple<>(state, node));
    }

    default Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        return new Ok<>(new Tuple<>(state, node));
    }
}
