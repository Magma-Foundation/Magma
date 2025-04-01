#ifndef magma_compile_rule_tree_TypeRule
#define magma_compile_rule_tree_TypeRule
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/NodeContext.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct TypeRule{
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
// expand Err<>
int __lambda0__();
int __lambda1__();
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
