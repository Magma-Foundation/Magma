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
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand List__Node = List_<struct Node>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
// expand Err_ = Err<struct >
// expand Result_String_CompileError = Result<struct String, struct CompileError>
// expand List__Node = List_<struct Node>
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node node);
struct Result_String_CompileError generateChildren(struct List__Node children);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda6__();
#endif

