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
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Ok<>
// expand magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Ok<>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.option.Tuple<>
auto __lambda0__();
auto __lambda1__();
magma.compile.Node asRoot(magma.collect.list.List_<magma.compile.Node> left);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> afterPass0(magma.compile.transform.State state, magma.compile.Node node);
magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> foldIntoBuckets(magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> tuple, magma.compile.Node node);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> beforePass0(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node);
#endif
