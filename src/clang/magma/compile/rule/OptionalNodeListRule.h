#ifndef magma_compile_rule_OptionalNodeListRule
#define magma_compile_rule_OptionalNodeListRule
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/rule/tree/OrRule.h"
#include "../../../magma/result/Result.h"
struct OptionalNodeListRule{struct String propertyKeystruct Rule ifPresentstruct Rule ifMissingstruct OrRule parseRule
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct public OptionalNodeListRule(struct String propertyKey, struct Rule ifPresent, struct Rule ifMissing);
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node node);
#endif

