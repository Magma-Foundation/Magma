#include "Compacter.h"
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){if (node.is("qualified")) {
            String last = Qualified.from(node)
                    .findLast()
                    .orElse("?");

            Node onlyLast = Qualified.to(Lists.of(last));
            return new Ok<>(new Tuple<>(state, onlyLast));
        }return ((state, node));
}
