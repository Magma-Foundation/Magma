#include "FlattenRoot.h"
int __lambda0__(){return (state, value);
}
int __lambda1__(){return (state, value);
}
Node afterPass0(Node node){if (!node.is("group")) return node;
        Node child = node.findNode("child").orElse(new MapNode());if (!child.is("root")) return node;
        List_<Node> oldChildren = child
                .findNode("content").orElse(new MapNode())
                .findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = node.streamNodeLists()
                .foldWithInitial(Lists.empty(), (nodeList, tuple) -> nodeList.addAll(tuple.right()));return MapNode("root").withNode("content", MapNode("block").withNodeList("children", oldChildren.addAll(newChildren)));
}
Result<Node, CompileError> afterPass0(State state, Node node){return (afterPass0(node));
}
Result<Node, CompileError> beforePass0(State state, Node node){return (node);
}
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
