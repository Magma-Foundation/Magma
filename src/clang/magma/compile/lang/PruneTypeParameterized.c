#include "PruneTypeParameterized.h"
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node){if (node.is("root")) {
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

