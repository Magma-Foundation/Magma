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
struct FlattenCache foldNodeProperty(struct FlattenCache state, Tuple<struct String, struct Node> property);
Tuple<struct Node, struct FlattenCache> flattenNode(struct FlattenCache cache, struct Node element);
struct FlattenCache flattenCategory(struct FlattenCache current, Tuple<struct String, List_<struct Node>> category);
struct FlattenCache flattenNodeList(struct FlattenCache cache, Tuple<struct String, List_<struct Node>> property);
Tuple<List_<struct Node>, struct FlattenCache> flattenNodeListElement(Tuple<List_<struct Node>, struct FlattenCache> tuple, struct Node node);
struct Node afterPass0(struct Node node);
Result<struct Node, struct CompileError> afterPass(struct State state, struct Node node);
Result<struct Node, struct CompileError> beforePass(struct State state, struct Node node);
#endif
