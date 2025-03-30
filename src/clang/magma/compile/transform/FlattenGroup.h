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
struct FlattenGroup{};
struct FlattenCache foldNodeProperty(struct FlattenCache state, struct Tuple_String_Node property);
struct Tuple_Node_FlattenCache flattenNode(struct FlattenCache cache, struct Node element);
struct FlattenCache flattenCategory(struct FlattenCache current, struct Tuple_String_List__Node category);
struct FlattenCache flattenNodeList(struct FlattenCache cache, struct Tuple_String_List__Node property);
struct Tuple_List__Node_FlattenCache flattenNodeListElement(struct Tuple_List__Node_FlattenCache tuple, struct Node node);
struct Node afterPass0(struct Node node);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
#endif
