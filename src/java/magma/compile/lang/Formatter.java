package magma.compile.lang;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.result.Ok;
import magma.result.Result;

public class Formatter implements Transformer {
    @Override
    public Result<Node, CompileError> afterPass(State state, Node node) {
        if (node.is("block")) {
            return new Ok<>(node.withString("before-children", "\n")
                    .withString("after-braces", "\n"));
        }

        return new Ok<>(node);
    }
}
