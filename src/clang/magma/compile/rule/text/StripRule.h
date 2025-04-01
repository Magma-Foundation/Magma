#ifndef magma_compile_rule_text_StripRule
#define magma_compile_rule_text_StripRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Result.h"
struct StripRule{
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
int __lambda0__();
public StripRule(Rule childRule);
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
String attachPadding(Node node, String value);
#endif
