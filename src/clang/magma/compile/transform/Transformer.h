#ifndef magma_compile_transform_Transformer
#define magma_compile_transform_Transformer
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct Transformer{
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
#endif

