#ifndef magma_compile_transform_Transformer
#define magma_compile_transform_Transformer
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/result/Result.h"
struct Transformer{};
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
#endif
