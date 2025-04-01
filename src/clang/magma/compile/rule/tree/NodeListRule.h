#ifndef magma_compile_rule_tree_NodeListRule
#define magma_compile_rule_tree_NodeListRule
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/MapNode.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/NodeContext.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/compile/rule/divide/Divider.h"
#include "../../../../magma/compile/rule/text/StringRule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct NodeListRule{
};
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Result<String, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.Node>
// expand magma.result.Err<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
int __lambda3__();
int __lambda4__();
int __lambda5__();
int __lambda6__();
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
magma.result.Result<String, magma.compile.CompileError> generateChildren(magma.collect.list.List_<magma.compile.Node> children);
#endif
