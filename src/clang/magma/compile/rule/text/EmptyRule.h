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
// expand Result<Node, CompileError>
// expand Ok<>
// expand Err<>
// expand Result<String, CompileError>
// expand Ok<>
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
