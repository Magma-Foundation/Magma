#include "ExpandGenerics.h"
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){if (node.is("generic")) {
            Node expansionGroup = new MapNode("group")
                    .withNode("child", node)
                    .withNodeList("expansions", Lists.of(node.retype("expansion")));

            return new Ok<>(new Tuple<>(state, expansionGroup));
        }return ((state, node));
}
