#ifndef magma_compile_lang_TransformAll
#define magma_compile_lang_TransformAll
#include "../../../windows/collect/list/Lists.h"
#include "../../../windows/collect/stream/Streams.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct TransformAll{int counter;
};
magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> bucketClassMember(magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> tuple, magma.compile.Node element);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> find(magma.compile.Node node, String propertyKey);
magma.result.Result<magma.collect.list.List_<magma.compile.Node>, magma.compile.CompileError> findNodeList(magma.compile.Node value, String propertyKey);
magma.compile.lang.boolean isFunctionalImport(magma.compile.Node child);
magma.compile.lang.boolean hasTypeParams(magma.compile.Node child);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node);
auto __lambda0__();
auto __lambda1__();
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
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<magma.collect.list.List_<magma.compile.Node>, magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<magma.collect.list.List_<magma.compile.Node>, magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
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
#endif

