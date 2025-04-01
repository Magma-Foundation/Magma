#include "Sorter.h"
int __lambda0__(){return (state, value);
}
int __lambda1__(){return (state, value);
}
Node asRoot(List_<Node> left){Node node1 = new MapNode("block").withNodeList("children", left);return MapNode("root").withNode("content", node1);
}
Result<Node, CompileError> afterPass0(State state, Node node){if (!node.is("root")) return new Ok<>(node);

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
                .withNode(".c", asRoot(sourceChildren));return (separated);
}
Tuple<List_<Node>, List_<Node>> foldIntoBuckets(Tuple<List_<Node>, List_<Node>> tuple, Node node){if (node.is("include") || node.is("struct") || node.is("expansion"))
            return new Tuple<>(tuple.left().add(node), tuple.right());if(node.is("function")) {
            return new Tuple<>(tuple.left().add(node.removeNode("content")), tuple.right().add(node));
        }return (tuple.left(), tuple.right().add(node));
}
Result<Node, CompileError> beforePass0(State state, Node node){return (node);
}
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
