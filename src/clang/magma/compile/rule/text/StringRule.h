#ifndef magma_compile_rule_text_StringRule
#define magma_compile_rule_text_StringRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/MapNode.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/NodeContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct StringRule{
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
// expand Result_String_CompileError = Result<struct String, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
// expand Err_ = Err<struct >
struct Result_Node_CompileError parse(struct String value);
struct Result_String_CompileError generate(struct Node input);
auto __lambda0__();
#endif

