#ifndef magma_compile_lang_FlattenStructs
#define magma_compile_lang_FlattenStructs
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
struct FlattenStructs{
};
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
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Ok<>
// expand Tuple<>
Tuple<List_<Node>, List_<Node>> bucketClassMember(Tuple<List_<Node>, List_<Node>> tuple, Node element);
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node);
#endif
