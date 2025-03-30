#ifndef magma_compile_rule_OptionalNodeRule
#define magma_compile_rule_OptionalNodeRule
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/rule/tree/OrRule.h"
#include "../../../magma/result/Result.h"
struct OptionalNodeRule{struct OrRule rulestruct String propertyKeystruct Rule ifPresentstruct Rule ifEmpty};
struct public OptionalNodeRule(struct String propertyKey, struct Rule ifPresent, struct Rule ifEmpty);
Result<struct Node, struct CompileError> parse(struct String input);
Result<struct String, struct CompileError> generate(struct Node node);
#endif
