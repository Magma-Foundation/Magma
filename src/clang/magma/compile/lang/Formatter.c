#include "Formatter.h"
struct Result_Node_CompileError afterPass(struct State state, struct Node node){if (node.is("block")) {
            return new Ok<>(node.withString("after-children", "\n"));
        }if (node.is("function")) {
            return new Ok<>(node.withString("after-braces", "\n"));
        }return Ok_(node);
}

