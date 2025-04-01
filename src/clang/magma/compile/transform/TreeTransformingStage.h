#ifndef magma_compile_transform_TreeTransformingStage
#define magma_compile_transform_TreeTransformingStage
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Result.h"
struct TreeTransformingStage{magma.compile.transform.Transformer transformer;
};
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<String, magma.compile.Node>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<>
// expand magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
int __lambda0__();
int __lambda1__();
int __lambda2__();
int __lambda3__();
int __lambda4__();
int __lambda5__();
int __lambda6__();
magma.compile.transform.public TreeTransformingStage(magma.compile.transform.Transformer transformer);
magma.option.Tuple<magma.compile.transform.State, magma.compile.Node> attachChildren(magma.compile.Node node, String propertyKey, magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>> children);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodes(magma.compile.transform.State state, magma.compile.Node root);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodeLists(magma.compile.transform.State state, magma.compile.Node root);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodes(magma.compile.transform.State state, magma.compile.Node node, magma.option.Tuple<String, magma.compile.Node> entry);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodeList(magma.compile.transform.State state, magma.compile.Node node, magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>> entry);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>>, magma.compile.CompileError> transformElement(magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>> current, magma.compile.Node element);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transform(magma.compile.transform.State state, magma.compile.Node root);
#endif
