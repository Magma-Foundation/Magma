#include "Formatter.h"
struct Result_Node_CompileError afterPass(struct State state, struct Node node){if (node.is()) {
            return new Ok<>(node.withString(, ));
        }if (node.is()) {
            return new Ok<>(node.withString(, ));
        }


        return new Ok<>(node);
}

