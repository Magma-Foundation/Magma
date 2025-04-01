#ifndef magma_compile_lang_FlattenRoot
#define magma_compile_lang_FlattenRoot
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct FlattenRoot{
};
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
Node afterPass0(Node node);
Result<Node, CompileError> afterPass0(State state, Node node);
Result<Node, CompileError> beforePass0(State state, Node node);
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node);
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node);
#endif
