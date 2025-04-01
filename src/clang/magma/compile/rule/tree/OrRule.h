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
// expand Result<T, CompileError>
// expand OrState<T>
// expand Result<T, CompileError>
// expand Result<T, CompileError>
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
// expand List_<Rule>
// expand Err<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
int __lambda3__();
int __lambda4__();
int __lambda5__();
Result<T, CompileError> apply(Result<T, CompileError>(*applicator)(Rule), Context(*context)());
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node input);
#endif
