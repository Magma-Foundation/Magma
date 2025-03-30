#include "Transformer.h"
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
struct Result_Node_CompileError beforePass(struct State state, struct Node node){return new Ok<>(node);}struct Result_Node_CompileError afterPass(struct State state, struct Node node);
