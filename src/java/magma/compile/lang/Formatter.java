package magma.compile.lang;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class Formatter implements Transformer {
    private Result<Node, CompileError> afterPass0(State state, Node node) {
        if (node.is("block")) {
            return new Ok<>(node.withString("after-children", "\n"));
        }

        if (node.is("function")) {
            return new Ok<>(node.withString("after-braces", "\n"));
        }


        return new Ok<>(node);
    }

    private Result<Node, CompileError> beforePass0(State state, Node node) {
        return new Ok<>(node);
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        return beforePass0(state, node).mapValue(value -> new Tuple<>(state, value));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        return afterPass0(state, node).mapValue(value -> new Tuple<>(state, value));
    }
}
