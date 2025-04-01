#include "FlattenRoot.h"
auto __lambda0__(){return (state, value);
}
auto __lambda1__(){return (state, value);
}
magma.compile.Node afterPass0(magma.compile.Node node){if (!node.is("group")) return node;
        Node child = node.findNode("child").orElse(new MapNode());if (!child.is("root")) return node;
        List_<Node> oldChildren = child
                .findNode("content").orElse(new MapNode())
                .findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = node.streamNodeLists()
                .foldWithInitial(Lists.empty(), (nodeList, tuple) -> nodeList.addAll(tuple.right()));return MapNode("root").withNode("content", MapNode("block").withNodeList("children", oldChildren.addAll(newChildren)));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> afterPass0(magma.compile.transform.State state, magma.compile.Node node){return (afterPass0(node));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> beforePass0(magma.compile.transform.State state, magma.compile.Node node){return (node);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
