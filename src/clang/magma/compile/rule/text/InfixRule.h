#ifndef magma_compile_rule_text_InfixRule
#define magma_compile_rule_text_InfixRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/compile/rule/locate/Locator.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct InfixRule{Rule left;String infix;Rule right;Locator locator;
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
int __lambda0__();
int __lambda1__();
public InfixRule(Rule left, String infix, Rule right, Locator locator);
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
