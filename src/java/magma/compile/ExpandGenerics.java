package magma.compile;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.lang.StringLists;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class ExpandGenerics implements Transformer {
    private static Option<List_<String>> qualifyName(State state, List_<String> name) {
        if (name.size() != 1) return new Some<>(name);
        return state.qualifyName(name.findFirst().orElse(""));
    }

    private static List_<String> attachNamespaceToName(State state, List_<String> name) {
        String last = name.findLast().orElse("");
        return state.namespace().add(last);
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        if (node.is("import")) {
            State newState = state.defineImport(node);
            return new Ok<>(new Tuple<>(newState, node));
        }

        if (node.is("qualified")) {
            List_<String> name = StringLists.fromSegments(node);
            List_<String> qualified = qualifyName(state, name)
                    .orElseGet(() -> attachNamespaceToName(state, name));

            Node qualifiedNode = StringLists.toSegments(qualified);
            return new Ok<>(new Tuple<>(state, qualifiedNode));
        }

        return new Ok<>(new Tuple<>(state, node));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        if (node.is("root")) {
            return new Ok<>(new Tuple<>(state.clearImports(), node));
        }

        Node type = node.findNode("type").orElse(new MapNode());
        if (type.is("generic")) {
            List_<String> value = StringLists.fromSegments(type.findNode("base").orElse(new MapNode()));

            if (value.equalsTo(Lists.of("java", "util", "function", "Function"))) {
                List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
                Node param = arguments.get(0);
                Node returns = arguments.get(1);

                Node definition = node.retype("functional-definition")
                        .removeNode("type")
                        .withNode("return", returns)
                        .withNodeList("params", Lists.of(param));

                return new Ok<>(new Tuple<>(state, definition));
            }

            if (value.equalsTo(Lists.of("java", "util", "function", "Supplier"))) {
                List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
                Node returns = arguments.get(0);

                Node definition = node.retype("functional-definition")
                        .removeNode("type")
                        .withNode("return", returns);

                return new Ok<>(new Tuple<>(state, definition));
            }

            return new Ok<>(new Tuple<>(state, node));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
