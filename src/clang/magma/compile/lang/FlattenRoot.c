#include "FlattenRoot.h"
struct Node afterPass0(struct Node node}{if (!node.is()) return node;
        Node child = node.findNode().orElse(new MapNode());if (!child.is()) return node;
        List_<Node> oldChildren = child
                .findNode().orElse(new MapNode())
                .findNodeList().orElse(Lists.empty());

        List_<Node> newChildren = node.streamNodeLists()
                .foldWithInitial(Lists.empty(), (nodeList, tuple) -> nodeList.addAll(tuple.right()));

        return new MapNode().withNode(, new MapNode().withNodeList(, oldChildren.addAll(newChildren)));}struct Result_Node_CompileError afterPass(struct State state, struct Node node}{return new Ok<>(afterPass0(node));}struct Result_Node_CompileError beforePass(struct State state, struct Node node}{return new Ok<>(node);}