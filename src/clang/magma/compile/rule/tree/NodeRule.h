#ifndef magma_compile_rule_tree_NodeRule
#define magma_compile_rule_tree_NodeRule
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/MapNode.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/NodeContext.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct NodeRule{
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
// expand Err<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
