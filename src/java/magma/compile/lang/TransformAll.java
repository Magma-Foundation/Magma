package magma.compile.lang;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.Transformer;
import magma.option.Tuple;

public class TransformAll implements Transformer {
    @Override
    public Node afterPass(Node node) {
        if (node.is("root")) {
            List_<Node> newChildren = node.findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .flatMap(child -> {
                        if (child.is("package")) {
                            return Streams.empty();
                        } else {
                            return Streams.of(child);
                        }
                    })
                    .collect(new ListCollector<Node>());

            return node.withNodeList("children", newChildren);
        }

        if (node.is("interface")) {
            Tuple<List_<Node>, List_<Node>> children = node.findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), TransformAll::bucketClassMember);

            Node withChildren = node.retype("struct").withNodeList("children", children.left());
            return new MapNode("group")
                    .withNode("child", withChildren)
                    .withNodeList("functions", children.right());
        }

        if (node.is("method")) {
            return node.retype("function");
        }

        return node;
    }

    static Tuple<List_<Node>, List_<Node>> bucketClassMember(Tuple<List_<Node>, List_<Node>> tuple, Node element) {
        List_<Node> definitions = tuple.left();
        List_<Node> others = tuple.right();

        if (element.is("definition")) {
            return new Tuple<List_<Node>, List_<Node>>(definitions.add(element), others);
        } else {
            return new Tuple<List_<Node>, List_<Node>>(definitions, others.add(element));
        }
    }
}