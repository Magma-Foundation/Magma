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
// expand magma.option.Tuple<String, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.Node, magma.compile.transform.FlattenCache>
// expand magma.option.Tuple<>
// expand magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.compile.transform.FlattenCache>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.compile.transform.FlattenCache>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Ok<>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Ok<>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
magma.compile.transform.FlattenCache foldNodeProperty(magma.compile.transform.FlattenCache state, magma.option.Tuple<String, magma.compile.Node> property);
magma.option.Tuple<magma.compile.Node, magma.compile.transform.FlattenCache> flattenNode(magma.compile.transform.FlattenCache cache, magma.compile.Node element);
magma.compile.transform.FlattenCache flattenCategory(magma.compile.transform.FlattenCache current, magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>> category);
magma.compile.transform.FlattenCache flattenNodeList(magma.compile.transform.FlattenCache cache, magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>> property);
magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.compile.transform.FlattenCache> flattenNodeListElement(magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.compile.transform.FlattenCache> tuple, magma.compile.Node node);
magma.compile.Node afterPass0(magma.compile.Node node);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> afterPass0(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> beforePass0(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node);
auto __lambda0__();
auto __lambda1__();
#endif

