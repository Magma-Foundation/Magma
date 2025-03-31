#ifndef magma_compile_rule_OptionalNodeListRule
#define magma_compile_rule_OptionalNodeListRule
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/rule/tree/OrRule.h"
#include "../../../magma/result/Result.h"
struct OptionalNodeListRule{String propertyKey;magma.compile.rule.Rule ifPresent;magma.compile.rule.Rule ifMissing;magma.compile.rule.tree.OrRule parseRule;
};
magma.compile.rule.public OptionalNodeListRule(String propertyKey, magma.compile.rule.Rule ifPresent, magma.compile.rule.Rule ifMissing);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
#endif

