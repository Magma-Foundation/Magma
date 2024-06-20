package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.Node;

import java.util.List;

public class JavaAnnotator extends Generator {
    private static Result<State, Error_> defineParams(State state, List<Node> params) {
        Result<State, Error_> defined = new Ok<>(state);
        for (Node param : params) {
            var name = param.findString("name").orElseThrow();
            defined = defined.flatMapValue(inner -> inner.define(name));
        }
        return defined;
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        if (node.is("lambda")) {
            var nameOptional = node.findString("param-name");
            if (nameOptional.isPresent()) {
                return state.define(nameOptional.get()).mapValue(inner -> new Tuple<>(node, inner));
            }
        }

        if (node.is("block")) {
            return new Ok<>(new Tuple<>(node, state.enter()));
        }

        if (node.is("method") || node.is("record")) {
            var params = node.findNodeList("params").orElseThrow();
            return defineParams(state, params).mapValue(inner -> new Tuple<>(node, inner));
        }

        if (node.is("class")) {
            var children = node.findNode("child")
                    .orElseThrow()
                    .findNodeList("children")
                    .orElseThrow();

            Result<State, Error_> defined = new Ok<>(state.enter());
            for (Node child : children) {
                if (child.is("method")) {
                    var name = child.findNode("definition")
                            .orElseThrow()
                            .findString("name")
                            .orElseThrow();

                    defined = defined.flatMapValue(inner -> inner.define(name));
                }
            }

            return defined.mapValue(inner -> new Tuple<>(node, inner));
        }

        return new Ok<>(new Tuple<>(node, state));
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        if (node.is("block")) {
            return new Ok<>(new Tuple<>(node, state.exit()));
        }

        if (node.is("class")) {
            return new Ok<>(new Tuple<>(node, state.exit()));
        }

        if (node.is("symbol")) {
            var value = node.findString("value").orElseThrow();
            if (value.equals("true") || value.equals("false") || value.equals("this") || state.isDefined(value)) {
                return new Ok<>(new Tuple<>(node, state));
            }

            return new Err<>(new CompileError("Symbol not defined.", value));
        }

        return new Ok<>(new Tuple<>(node, state));
    }
}
