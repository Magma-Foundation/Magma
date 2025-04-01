#include "PruneTypeParameterized.h"
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){if (node.is("root")) {
            List_<Node> children = node.findNode("content").orElse(new MapNode())
                    .findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .filter(child -> !child.hasNodeList("type-params"))
                    .collect(new ListCollector<>());

            Node block = new MapNode("block").withNodeList("children", children);
            return new Ok<>(new Tuple<>(state, node.withNode("content", block)));
        }return ((state, node));
}
