#ifndef magma_compile_rule_text_PrefixRule
#define magma_compile_rule_text_PrefixRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct PrefixRule{
};
// expand Result<Node, CompileError>
// expand Err<>
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
int __lambda0__();
Result<Node, CompileError> createPrefixErr(String input, String prefix);
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
