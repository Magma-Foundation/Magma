#ifndef magma_compile_lang_ExpandGenerics
#define magma_compile_lang_ExpandGenerics
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct ExpandGenerics{
};
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Ok<>
// expand Tuple<>
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node);
#endif
