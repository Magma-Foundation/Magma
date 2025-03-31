package magma.compile;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class ExpandGenerics implements Transformer {
    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        if (node.is("import")) {
            State newState = state.defineImport(node);
            return new Ok<>(new Tuple<>(newState, node));
        }

        if (!node.is("definition")) {
            return new Ok<>(new Tuple<>(state, node));
        }

        Node type = node.findNode("type").orElse(new MapNode());
        if (!type.is("generic"))
            return new Ok<>(new Tuple<>(state, node));

        String value = type.findNodeList("base")
                .orElse(Lists.empty())
                .get(0)
                .findString("value")
                .orElse("");

        if (value.equals("Function")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node param = arguments.get(0);
            Node returns = arguments.get(1);

            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param));

            return new Ok<>(new Tuple<>(state, definition));
        }

        if (value.equals("Supplier")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node returns = arguments.get(0);

            Node definition = node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns);

            return new Ok<>(new Tuple<>(state, definition));
        }

        return new Ok<>(new Tuple<>(state, node));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        if (node.is("root")) {
            return new Ok<>(new Tuple<>(state.clearImports(), node));
        }

        if (node.is("generic")) {
            List_<String> name = node.findNodeList("base")
                    .orElse(Lists.empty())
                    .stream()
                    .map(segment -> segment.findString("value"))
                    .flatMap(Streams::fromOption)
                    .collect(new ListCollector<>());

            List_<Node> wrappedSegments = state.resolve(name)
                    .orElse(state.namespace().add(name.findLast().orElse("")))
                    .stream()
                    .map(segment -> new MapNode().withString("value", segment))
                    .collect(new ListCollector<>());

            Node wrappedNode = new MapNode("expansion").withNodeList("namespace", wrappedSegments);

            Node group = new MapNode("group")
                    .withNode("child", node)
                    .withNodeList("expansions", Lists.of(wrappedNode));

            return new Ok<>(new Tuple<>(state, group));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
