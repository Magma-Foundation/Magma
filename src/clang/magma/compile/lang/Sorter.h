#ifndef magma_compile_lang_Sorter
#define magma_compile_lang_Sorter
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/stream/Joiner.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct Sorter{
};
// expand List_<Node>
// expand Result<Node, CompileError>
// expand Ok<>
// expand Tuple<List_<Node>, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
// expand Tuple<>
// expand Tuple<List_<Node>, List_<Node>>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
// expand List_<Node>
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
Node asRoot(List_<Node> left);
Result<Node, CompileError> afterPass0(State state, Node node);
Tuple<List_<Node>, List_<Node>> foldIntoBuckets(Tuple<List_<Node>, List_<Node>> tuple, Node node);
Result<Node, CompileError> beforePass0(State state, Node node);
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node);
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node);
#endif
