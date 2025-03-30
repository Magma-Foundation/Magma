#ifndef magma_compile_lang_FlattenRoot
#define magma_compile_lang_FlattenRoot
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct FlattenRoot{
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
struct Node afterPass0(struct Node node);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
#endif

