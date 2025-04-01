#include "FlattenStructs.h"
Tuple<List_<Node>, List_<Node>> bucketClassMember(Tuple<List_<Node>, List_<Node>> tuple, Node element){List_<Node> definitions = tuple.left();
        List_<Node> others = tuple.right();if (element.is("definition")) return new Tuple<>(definitions.add(element), others);if (element.is("initialization")) {
            Node definition = element.findNode("definition").orElse(new MapNode());
            return new Tuple<>(definitions.add(definition), others);
        }return (definitions, others.add(element));
}
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){if (node.is("interface") || node.is("record") || node.is("class")) {
            return Transformers.findNode(node, "content").flatMapValue(value -> {
                return Transformers.findNodeList(value, "children").mapValue(children -> {
                    Tuple<List_<Node>, List_<Node>> newChildren = children.stream()
                            .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), FlattenStructs::bucketClassMember);

                    Node withChildren = node.retype("struct").withNode("content", new MapNode("block")
                            .withNodeList("children", newChildren.left()));

                    return new Tuple<>(state, new MapNode("group")
                            .withNode("child", withChildren)
                            .withNodeList("functions", newChildren.right()));
                });
            });
        }return ((state, node));
}
