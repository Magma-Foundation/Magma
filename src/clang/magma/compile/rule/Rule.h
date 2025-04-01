#ifndef magma_compile_rule_Rule
#define magma_compile_rule_Rule
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/result/Result.h"
struct Rule{
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
