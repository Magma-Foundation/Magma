#ifndef magma_compile_ExpandGenerics
#define magma_compile_ExpandGenerics
#include "../../windows/collect/list/JavaList.h"
#include "../../windows/collect/list/Lists.h"
#include "../../windows/collect/stream/Streams.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/stream/Joiner.h"
#include "../../magma/compile/transform/State.h"
#include "../../magma/compile/transform/Transformer.h"
#include "../../magma/result/Ok.h"
#include "../../magma/result/Result.h"
#include "../../java/util/Map.h"
struct ExpandGenerics{
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
struct String stringify(struct Node node);
auto __lambda0__();
#endif

