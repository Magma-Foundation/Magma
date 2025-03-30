#ifndef magma_compile_transform_TransformingStage
#define magma_compile_transform_TransformingStage
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/result/Result.h"
struct TransformingStage{
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
struct Result_Node_CompileError transform(struct Node root, struct State state);
#endif

