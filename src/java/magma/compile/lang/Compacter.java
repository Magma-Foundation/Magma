package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class Compacter implements Transformer {
    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        if (node.is("qualified")) {
            String last = Qualified.from(node)
                    .findLast()
                    .orElse("?");

            Node onlyLast = Qualified.to(Lists.of(last));
            return new Ok<>(new Tuple<>(state, onlyLast));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
