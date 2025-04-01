#ifndef magma_compile_rule_OptionalNodeListRule
#define magma_compile_rule_OptionalNodeListRule
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/rule/tree/OrRule.h"
#include "../../../magma/result/Result.h"
struct OptionalNodeListRule{String propertyKey;Rule ifPresent;Rule ifMissing;OrRule parseRule;
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
public OptionalNodeListRule(String propertyKey, Rule ifPresent, Rule ifMissing);
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
