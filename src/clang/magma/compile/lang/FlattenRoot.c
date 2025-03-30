#include "FlattenRoot.h"
struct Node afterPass0(struct Node node){if (!node.is("group")) return node;
        Node child = node.findNode("child").orElse(new MapNode());if (!child.is("root")) return node;
        List_<Node> oldChildren = child
                .findNode("content").orElse(new MapNode())
                .findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = node.streamNodeLists()
                .foldWithInitial(Lists.empty(), (nodeList, tuple) -> nodeList.addAll(tuple.right()));return MapNode("root").withNode("content", MapNode("block").withNodeList("children", oldChildren.addAll(newChildren)));
}
struct Result_Node_CompileError afterPass(struct State state, struct Node node){return Ok_(afterPass0(node));
}
struct Result_Node_CompileError beforePass(struct State state, struct Node node){return Ok_(node);
}

