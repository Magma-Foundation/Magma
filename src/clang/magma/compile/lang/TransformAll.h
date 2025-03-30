#ifndef magma_compile_lang_TransformAll
#define magma_compile_lang_TransformAll
#include "../../../windows/collect/list/Lists.h"
#include "../../../windows/collect/stream/Streams.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct TransformAll{int counter
};
// expand Tuple_List__Node_List__Node = Tuple<struct List__Node, struct List__Node>
// expand List__Node = List_<struct Node>
// expand List__Node = List_<struct Node>
// expand Tuple_ = Tuple<struct >
// expand Tuple_List__Node_List__Node = Tuple<struct List__Node, struct List__Node>
// expand List__Node = List_<struct Node>
// expand List__Node = List_<struct Node>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Err_ = Err<struct >
// expand Result_List__Node_CompileError = Result<struct List__Node, struct CompileError>
// expand List__Node = List_<struct Node>
// expand Result_List__Node_CompileError = Result<struct List__Node, struct CompileError>
// expand List__Node = List_<struct Node>
// expand Err_ = Err<struct >
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
struct Tuple_List__Node_List__Node bucketClassMember(struct Tuple_List__Node_List__Node tuple, struct Node element);
struct Result_Node_CompileError find(struct Node node, struct String propertyKey);
struct Result_List__Node_CompileError findNodeList(struct Node value, struct String propertyKey);
int isFunctionalImport(struct Node child);
int hasTypeParams(struct Node child);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
auto __lambda0__();
auto __lambda1__();
#endif

