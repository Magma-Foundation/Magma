package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.Node;

public class JavaAnnotator extends Generator {
    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        if(node.is("symbol")) {
            var value = node.findString("value").orElseThrow();
            if(!state.isDefined(value)) {
                return new Err<>(new CompileError("Symbol not defined.", value));
            }
        }

        return new Ok<>(new Tuple<>(node, state));
    }
}
