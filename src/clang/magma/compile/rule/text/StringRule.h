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
// expand Result<Node, CompileError>
// expand Ok<>
// expand Result<String, CompileError>
// expand Result<String, CompileError>
// expand Err<>
int __lambda0__();
Result<Node, CompileError> parse(String value);
Result<String, CompileError> generate(Node input);
#endif
