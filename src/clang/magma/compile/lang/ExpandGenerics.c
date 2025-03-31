#include "ExpandGenerics.h"
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node){if (node.is("generic")) {
            Node expansionGroup = new MapNode("group")
                    .withNode("child", node)
                    .withNodeList("expansions", Lists.of(node.retype("expansion")));

            return new Ok<>(new Tuple<>(state, expansionGroup));
        }return ((state, node));
}
