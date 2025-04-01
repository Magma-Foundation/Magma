#include "Formatter.h"
int __lambda0__(){return (state, value);
}
int __lambda1__(){return (state, value);
}
Result<Node, CompileError> afterPass0(State state, Node node){if (node.is("block")) {
            return new Ok<>(node.withString("after-children", "\n"));
        }if (node.is("function")) {
            return new Ok<>(node.withString("after-braces", "\n"));
        }return (node);
}
Result<Node, CompileError> beforePass0(State state, Node node){return (node);
}
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
