#ifndef magma_compile_rule_OptionalNodeRule
#define magma_compile_rule_OptionalNodeRule
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/rule/tree/OrRule.h"
#include "../../../magma/result/Result.h"
struct OptionalNodeRule{OrRule rule;String propertyKey;Rule ifPresent;Rule ifEmpty;
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
public OptionalNodeRule(String propertyKey, Rule ifPresent, Rule ifEmpty);
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
