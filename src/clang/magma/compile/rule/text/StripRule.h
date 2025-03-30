#ifndef magma_compile_rule_text_StripRule
#define magma_compile_rule_text_StripRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Result.h"
struct StripRule{};
Result<struct Node, struct CompileError> parse(struct String input);
Result<struct String, struct CompileError> generate(struct Node node);
#endif
