#ifndef magma_compile_rule_tree_OrRule
#define magma_compile_rule_tree_OrRule
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/Context.h"
#include "../../../../magma/compile/context/NodeContext.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct OrRule{
};
// expand magma.result.Result<magma.compile.rule.tree.T, magma.compile.CompileError>
// expand magma.compile.rule.tree.OrState<magma.compile.rule.tree.T>
// expand magma.result.Result<magma.compile.rule.tree.T, magma.compile.CompileError>
// expand magma.result.Result<magma.compile.rule.tree.T, magma.compile.CompileError>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.rule.Rule>
// expand magma.result.Err<>
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
magma.result.Result<magma.compile.rule.tree.T, magma.compile.CompileError> apply(magma.result.Result<magma.compile.rule.tree.T, magma.compile.CompileError>(*applicator)(magma.compile.rule.Rule), magma.compile.context.Context(*context)());
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node input);
#endif
