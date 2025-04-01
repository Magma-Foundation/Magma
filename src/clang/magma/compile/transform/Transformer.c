#include "Transformer.h"
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){return ((state, node));
}
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){return ((state, node));
}
