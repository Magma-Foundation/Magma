#ifndef magma_compile_transform_TreeTransformingStage
#define magma_compile_transform_TreeTransformingStage
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Result.h"
struct TreeTransformingStage{Transformer transformer;
};
// expand Tuple<State, Node>
// expand Tuple<>
// expand Tuple<State, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Tuple<>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Tuple<>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Tuple<String, Node>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Tuple<>
// expand Tuple<String, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Result<Tuple<State, List_<Node>>, CompileError>
// expand Tuple<State, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<State, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<State, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
int __lambda0__();
int __lambda1__();
int __lambda2__();
int __lambda3__();
int __lambda4__();
int __lambda5__();
int __lambda6__();
public TreeTransformingStage(Transformer transformer);
Tuple<State, Node> attachChildren(Node node, String propertyKey, Tuple<State, List_<Node>> children);
Result<Tuple<State, Node>, CompileError> transformNodes(State state, Node root);
Result<Tuple<State, Node>, CompileError> transformNodeLists(State state, Node root);
Result<Tuple<State, Node>, CompileError> transformNodes(State state, Node node, Tuple<String, Node> entry);
Result<Tuple<State, Node>, CompileError> transformNodeList(State state, Node node, Tuple<String, List_<Node>> entry);
Result<Tuple<State, List_<Node>>, CompileError> transformElement(Tuple<State, List_<Node>> current, Node element);
Result<Tuple<State, Node>, CompileError> transform(State state, Node root);
#endif
