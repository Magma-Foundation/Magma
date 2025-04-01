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
// expand magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.option.Some<>
// expand magma.result.Ok<>
// expand magma.collect.list.List_<String>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.option.Option<String>
// expand magma.option.None<>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.collect.list.List_<String>
// expand magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.result.Ok<>
// expand magma.option.Tuple<>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.result.Ok<>
// expand magma.option.Tuple<>
// expand magma.result.Ok<>
// expand magma.result.Err<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>> resolveAsImport(magma.compile.transform.State state, magma.collect.list.List_<String> name);
magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError> resolveAsLocal(magma.compile.transform.State state, magma.collect.list.List_<String> name, magma.compile.Node node);
magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>> resolveAsPrimitive(magma.collect.list.List_<String> oldName);
magma.option.Option<String> resolvePrimitiveString(String maybePrimitive);
magma.option.Tuple<magma.compile.transform.State, magma.compile.Node> wrapToTuple(magma.compile.transform.State state, magma.collect.list.List_<String> newName);
magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>> resolveAsTypeParam(magma.compile.transform.State state, magma.collect.list.List_<String> segments);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node);
#endif
