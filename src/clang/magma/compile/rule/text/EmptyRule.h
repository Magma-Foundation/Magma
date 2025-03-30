#ifndef magma_compile_rule_text_EmptyRule
#define magma_compile_rule_text_EmptyRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/MapNode.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct EmptyRule{
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Ok_ = Ok<struct >
// expand Err_ = Err<struct >
// expand Result_String_CompileError = Result<struct String, struct CompileError>
// expand Ok_ = Ok<struct >
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node node);
#endif

