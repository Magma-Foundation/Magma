#ifndef magma_compile_transform_Transformer
#define magma_compile_transform_Transformer
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/result/Result.h"
struct Transformer{};
Result<struct Node, struct CompileError> beforePass(struct State state, struct Node node);
Result<struct Node, struct CompileError> afterPass(struct State state, struct Node node);
#endif
