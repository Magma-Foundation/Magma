package magma.compile.lang;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class TransformAll implements Transformer {
    static Tuple<List_<Node>, List_<Node>> bucketClassMember(Tuple<List_<Node>, List_<Node>> tuple, Node element) {
        List_<Node> definitions = tuple.left();
        List_<Node> others = tuple.right();

        if (element.is("definition")) {
            return new Tuple<>(definitions.add(element), others);
        } else {
            return new Tuple<>(definitions, others.add(element));
        }
    }

    @Override
    public Result<Node, CompileError> afterPass(Node node) {
        if (node.is("root")) {
            List_<Node> newChildren = node.findNode("value")
                    .orElse(new MapNode())
                    .findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .flatMap(child -> {
                        if (child.is("package")) {
                            return Streams.empty();
                        } else {
                            return Streams.of(child);
                        }
                    })
                    .collect(new ListCollector<>());

            return new Ok<>(node.withNode("content", new MapNode("block")
                    .withNodeList("children", newChildren)));
        }

        if (node.is("interface")) {
            Tuple<List_<Node>, List_<Node>> children = node.findNode("value")
                    .orElse(new MapNode())
                    .findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), TransformAll::bucketClassMember);

            Node withChildren = node.retype("struct").withNode("content", new MapNode("block").withNodeList("children", children.left()));

            return new Ok<>(new MapNode("group")
                    .withNode("child", withChildren)
                    .withNodeList("functions", children.right()));
        }

        if (node.is("method")) {
            return new Ok<>(node.retype("function"));
        }

        return new Ok<>(node);
    }
}