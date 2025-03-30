#ifndef magma_compile_transform_FlattenGroup
#define magma_compile_transform_FlattenGroup
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct FlattenGroup{
};
// expand Tuple_String_Node = Tuple<struct String, struct Node>
// expand Tuple_Node_FlattenCache = Tuple<struct Node, struct FlattenCache>
// expand Tuple_ = Tuple<struct >
// expand Tuple_String_List__Node = Tuple<struct String, struct List__Node>
// expand List__Node = List_<struct Node>
// expand Tuple_String_List__Node = Tuple<struct String, struct List__Node>
// expand List__Node = List_<struct Node>
// expand Tuple_List__Node_FlattenCache = Tuple<struct List__Node, struct FlattenCache>
// expand List__Node = List_<struct Node>
// expand Tuple_ = Tuple<struct >
// expand Tuple_List__Node_FlattenCache = Tuple<struct List__Node, struct FlattenCache>
// expand List__Node = List_<struct Node>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
struct FlattenCache foldNodeProperty(struct FlattenCache state, struct Tuple_String_Node property);
struct Tuple_Node_FlattenCache flattenNode(struct FlattenCache cache, struct Node element);
struct FlattenCache flattenCategory(struct FlattenCache current, struct Tuple_String_List__Node category);
struct FlattenCache flattenNodeList(struct FlattenCache cache, struct Tuple_String_List__Node property);
struct Tuple_List__Node_FlattenCache flattenNodeListElement(struct Tuple_List__Node_FlattenCache tuple, struct Node node);
struct Node afterPass0(struct Node node);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
#endif

