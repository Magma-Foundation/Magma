#ifndef magma_compile_lang_ResolveTypes
#define magma_compile_lang_ResolveTypes
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct ResolveTypes{
};
// expand Option<Result<List_<String>, CompileError>>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand Some<>
// expand Ok<>
// expand List_<String>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand List_<String>
// expand Option<Result<List_<String>, CompileError>>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand List_<String>
// expand Option<String>
// expand None<>
// expand Tuple<State, Node>
// expand Tuple<>
// expand List_<String>
// expand Option<Result<List_<String>, CompileError>>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand Result<List_<String>, CompileError>
// expand List_<String>
// expand List_<String>
// expand List_<String>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Ok<>
// expand Tuple<>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Ok<>
// expand Tuple<>
// expand Ok<>
// expand Err<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
Option<Result<List_<String>, CompileError>> resolveAsImport(State state, List_<String> name);
Result<List_<String>, CompileError> resolveAsLocal(State state, List_<String> name, Node node);
Option<Result<List_<String>, CompileError>> resolveAsPrimitive(List_<String> oldName);
Option<String> resolvePrimitiveString(String maybePrimitive);
Tuple<State, Node> wrapToTuple(State state, List_<String> newName);
Option<Result<List_<String>, CompileError>> resolveAsTypeParam(State state, List_<String> segments);
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node);
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node);
#endif
