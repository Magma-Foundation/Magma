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
// expand Result_T_CompileError = Result<struct T, struct CompileError>
// expand OrState_T = OrState<struct T>
// expand Result_T_CompileError = Result<struct T, struct CompileError>
// expand Err_ = Err<struct >
// expand Result_T_CompileError = Result<struct T, struct CompileError>
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
// expand List__Rule = List_<struct Rule>
struct Result_T_CompileError apply(struct Result_T_CompileError(*applicator)(struct Rule), struct Context(*context)());
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node input);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
#endif

