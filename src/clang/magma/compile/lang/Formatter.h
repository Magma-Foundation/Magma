#ifndef magma_compile_lang_Formatter
#define magma_compile_lang_Formatter
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct Formatter{
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
#endif

