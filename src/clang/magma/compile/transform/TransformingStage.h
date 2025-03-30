#ifndef magma_compile_transform_TransformingStage
#define magma_compile_transform_TransformingStage
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/result/Result.h"
struct TransformingStage{};
Result<struct Node, struct CompileError> transform(struct Node root, struct State state);
#endif
