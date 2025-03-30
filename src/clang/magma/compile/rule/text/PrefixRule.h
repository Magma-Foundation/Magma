#ifndef magma_compile_rule_text_PrefixRule
#define magma_compile_rule_text_PrefixRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct PrefixRule{};
Result<struct Node, struct CompileError> createPrefixErr(struct String input, struct String prefix);
Result<struct Node, struct CompileError> parse(struct String input);
Result<struct String, struct CompileError> generate(struct Node node);
#endif
