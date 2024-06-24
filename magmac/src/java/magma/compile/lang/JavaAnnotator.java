package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.rule.Node;
import magma.java.JavaList;

import java.util.List;

public class JavaAnnotator extends TreeGenerator {
    private static Result<State, Error_> defineParams(State state, List<Node> params) {
        Result<State, Error_> defined = new Ok<>(state);
        for (Node param : params) {
            var name = param.findString("name").orElseThrow();
            defined = defined.flatMapValue(inner -> inner.define(name));
        }
        return defined;
    }

    private static Result<State, Error_> hoistMethods(State state, List<Node> children) {
        Result<State, Error_> defined = new Ok<>(state.enter());
        for (Node child : children) {
            if (child.is("method")) {
                var name = child.findNode("definition")
                        .orElseThrow()
                        .findString("name")
                        .orElseThrow();

                defined = defined.flatMapValue(inner -> inner.define(name));
            }

            if (child.is("class") || child.is("record") || child.is("interface")) {
                var name = child.findString("name").orElseThrow();
                defined = defined.flatMapValue(inner -> inner.define(name));
            }
        }
        return defined;
    }

    private static Result<Tuple<Node, State>, Error_> defineParams(Node node, State state) {
        var params = node.findNodeList("params").orElseThrow();
        return defineParams(state, params).mapValue(inner -> new Tuple<>(node, inner));
    }

    private static Result<Tuple<Node, State>, Error_> define(Node node, State state, Node definition) {
        var name = definition.findString("name").orElseThrow();
        return state.define(name).mapValue(inner -> new Tuple<>(node, inner));
    }

    private static boolean exists(State state, String value) {
        var b = value.equals("true")
                || value.equals("false")
                || value.equals("this")
                || value.equals("super")
                || state.isDefined(value);
        return b || isInJavaLang(value);
    }

    private static boolean isInJavaLang(String value) {
        try {
            Class.forName("java.lang." + value);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        if (node.is("import")) {
            var namespace = node.findNode("external")
                    .orElseThrow()
                    .findStringList("namespace")
                    .orElseThrow();

            var last = namespace.get(namespace.size() - 1);
            return state.define(last).mapValue(newState -> new Tuple<>(node, newState));
        }

        if (node.is("lambda")) {
            var nameOptional = node.findString("param");
            if (nameOptional.isPresent()) {
                return state.define(nameOptional.get()).mapValue(inner -> new Tuple<>(node, inner));
            }

            var params = node.findStringList("params");
            if (params.isPresent()) {
                return state.defineAll(new JavaList<>(params.get())).mapValue(inner -> new Tuple<>(node, inner));
            }
        }

        if (node.is("for")) {
            var name = node.findNode("condition-parent")
                    .orElseThrow()
                    .findString("name")
                    .orElseThrow();

            return state.enter().define(name).mapValue(inner -> new Tuple<>(node, inner));
        }

        if (node.is("block")) {
            return new Ok<>(new Tuple<>(node, state.enter()));
        }

        if (node.is("method")) {
            return defineParams(node, state);
        }

        if (node.is("constructor")) {
            var children = node.findNodeList("children");
            if (children.isPresent()) {
                return hoistMethods(state, children.get()).mapValue(inner -> new Tuple<>(node, inner));
            }
        }

        if (node.is("class") || node.is("interface")) {
            return hoistMethods(state, node.findNode("child")
                    .orElseThrow().findNodeList("children").orElseThrow()).mapValue(inner -> new Tuple<>(node, inner));
        }

        if (node.is("record")) {
            return defineParams(node, state).flatMapValue(inner -> hoistMethods(inner.right(), inner.left().findNode("child")
                    .orElseThrow().findNodeList("children").orElseThrow()).mapValue(inner1 -> new Tuple<>(inner.left(), inner1)));
        }

        return new Ok<>(new Tuple<>(node, state));
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        if (node.is("block") || node.is("for") || node.is("class")) {
            return new Ok<>(new Tuple<>(node, state.exit()));
        }

        if (node.is("symbol")) {
            var value = node.findString("value").orElseThrow();
            if (exists(state, value)) {
                return new Ok<>(new Tuple<>(node, state));
            }

            return new Err<>(new CompileError("Symbol not defined.", value));
        }

        if (node.is("declaration")) {
            var definition = node.findNode("definition").orElseThrow();
            return define(node, state, definition);
        }

        if (node.is("definition")) {
            return define(node, state, node);
        }

        return new Ok<>(new Tuple<>(node, state));
    }
}
