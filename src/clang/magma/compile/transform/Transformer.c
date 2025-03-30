#include "Transformer.h"
Result<struct Node, struct CompileError> beforePass(struct State state, struct Node node);
Result<struct Node, struct CompileError> afterPass(struct State state, struct Node node);
