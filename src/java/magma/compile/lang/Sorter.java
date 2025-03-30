package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

import java.util.function.BiFunction;

public class Sorter implements Transformer {
    private static Node asRoot(List_<Node> left) {
        Node node1 = new MapNode("block").withNodeList("children", left);
        return new MapNode("root").withNode("content", node1);
    }

    @Override
    public Result<Node, CompileError> afterPass(List_<String> namespace, Node node) {
        if (node.is("root")) {
            List_<Node> children = node.findNode("content").orElse(new MapNode())
                    .findNodeList("children").orElse(Lists.empty());

            Tuple<List_<Node>, List_<Node>> tuple = children.stream().foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), new BiFunction<Tuple<List_<Node>, List_<Node>>, Node, Tuple<List_<Node>, List_<Node>>>() {
                @Override
                public Tuple<List_<Node>, List_<Node>> apply(Tuple<List_<Node>, List_<Node>> tuple, Node node) {
                    if (node.is("include") || node.is("struct"))
                        return new Tuple<>(tuple.left().add(node), tuple.right());
                    return new Tuple<>(tuple.left(), tuple.right().add(node));
                }
            });

            Node separated = new MapNode()
                    .withNode(".h", asRoot(tuple.left()))
                    .withNode(".c", asRoot(tuple.right()));

            return new Ok<>(separated);
        }

        return new Ok<>(node);
    }
}
