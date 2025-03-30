#ifndef magma_compile_transform_TreeTransformingStage
#define magma_compile_transform_TreeTransformingStage
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Result.h"
struct TreeTransformingStage{struct Transformer transformer
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Tuple_String_Node = Tuple<struct String, struct Node>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Tuple_String_List__Node = Tuple<struct String, struct List__Node>
// expand List__Node = List_<struct Node>
// expand Result_List__Node_CompileError = Result<struct List__Node, struct CompileError>
// expand List__Node = List_<struct Node>
// expand List__Node = List_<struct Node>
struct public TreeTransformingStage(struct Transformer transformer);
struct Result_Node_CompileError transform(struct Node root, struct State state);
struct Result_Node_CompileError transformNodes(struct Node root, struct State state);
struct Result_Node_CompileError transformNodeLists(struct Node root, struct State state);
struct Result_Node_CompileError mapNodes(struct Node node, struct Tuple_String_Node tuple, struct State state);
struct Result_Node_CompileError mapNodeList(struct Node node, struct Tuple_String_List__Node tuple, struct State state);
struct Result_List__Node_CompileError mapNodeListElement(struct List__Node elements, struct Node element, struct State state);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();
#endif

