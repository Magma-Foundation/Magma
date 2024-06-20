package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.Node;

import java.util.Optional;

public class JavaAnnotator extends Generator {
    private Optional<Result<Tuple<Node, State>, Error_>> postVisitSymbol(Node node, State state) {
        if (!node.is("symbol")) return Optional.empty();

        var value = node.findString("value").orElseThrow();
        if (value.equals("true") || value.equals("false") || state.isDefined(value)) {
            return Optional.of(new Ok<>(new Tuple<>(node, state)));
        } else {
            return Optional.of(new Err<>(new CompileError("Value is not defined.", value)));
        }
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        return postVisitSymbol(node, state).orElse(new Ok<>(new Tuple<>(node, state)));
    }
}
