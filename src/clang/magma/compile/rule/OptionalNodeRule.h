#ifndef magma_compile_rule_OptionalNodeRule
#define magma_compile_rule_OptionalNodeRule
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/rule/tree/OrRule.h"
#include "../../../magma/result/Result.h"
struct OptionalNodeRule{magma.compile.rule.tree.OrRule rule;String propertyKey;magma.compile.rule.Rule ifPresent;magma.compile.rule.Rule ifEmpty;
};
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
magma.compile.rule.public OptionalNodeRule(String propertyKey, magma.compile.rule.Rule ifPresent, magma.compile.rule.Rule ifEmpty);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
#endif
