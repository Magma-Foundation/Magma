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
// expand Tuple<String, Node>
// expand Tuple<Node, FlattenCache>
// expand Tuple<>
// expand Tuple<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<List_<Node>, FlattenCache>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<>
// expand Tuple<List_<Node>, FlattenCache>
// expand List_<Node>
// expand List_<Node>
// expand Result<Node, CompileError>
// expand Ok<>
// expand Result<Node, CompileError>
// expand Ok<>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Tuple<>
// expand Tuple<>
int __lambda0__();
int __lambda1__();
FlattenCache foldNodeProperty(FlattenCache state, Tuple<String, Node> property);
Tuple<Node, FlattenCache> flattenNode(FlattenCache cache, Node element);
FlattenCache flattenCategory(FlattenCache current, Tuple<String, List_<Node>> category);
FlattenCache flattenNodeList(FlattenCache cache, Tuple<String, List_<Node>> property);
Tuple<List_<Node>, FlattenCache> flattenNodeListElement(Tuple<List_<Node>, FlattenCache> tuple, Node node);
Node afterPass0(Node node);
Result<Node, CompileError> afterPass0(State state, Node node);
Result<Node, CompileError> beforePass0(State state, Node node);
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node);
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node);
#endif
