#ifndef magma_compile_rule_text_SuffixRule
#define magma_compile_rule_text_SuffixRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct SuffixRule{};
Result<struct Node, struct CompileError> parse(struct String input);
Result<struct String, struct CompileError> generate(struct Node node);
#endif
