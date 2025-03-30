#include "Sorter.h"
struct Node asRoot(struct List__Node left){Node node1 = new MapNode("block").withNodeList("children", left);return MapNode("root").withNode("content", node1);
}
struct Result_Node_CompileError afterPass(struct State state, struct Node node){if (!node.is("root")) return new Ok<>(node);

        List_<Node> children = node.findNode("content").orElse(new MapNode())
                .findNodeList("children").orElse(Lists.empty());

        Tuple<List_<Node>, List_<Node>> tuple = children.stream().foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), Sorter::foldIntoBuckets);

        String joined = state.namespace()
                .add(state.name())
                .stream()
                .collect(new Joiner("_"))
                .orElse("");

        List_<Node> headerChildren = Lists.<Node>empty()
                .add(new MapNode("ifndef").withString("value", joined))
                .add(new MapNode("define").withString("value", joined))
                .addAll(tuple.left())
                .add(new MapNode("endif"));

        List_<Node> sourceChildren = Lists.<Node>empty()
                .add(new MapNode("include").withNodeList("path", Lists.of(new MapNode().withString("value", state.name()))))
                .addAll(tuple.right());

        Node separated = new MapNode()
                .withNode(".h", asRoot(headerChildren))
                .withNode(".c", asRoot(sourceChildren));return Ok_(separated);
}
struct Tuple_List__Node_List__Node foldIntoBuckets(struct Tuple_List__Node_List__Node tuple, struct Node node){if (node.is("include") || node.is("struct") || node.is("expansion"))
            return new Tuple<>(tuple.left().add(node), tuple.right());if(node.is("function")) {
            return new Tuple<>(tuple.left().add(node.removeNode("content")), tuple.right().add(node));
        }return Tuple_(tuple.left(), tuple.right().add(node));
}

