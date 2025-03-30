#ifndef magma_compile_rule_Rule
#define magma_compile_rule_Rule
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/result/Result.h"
struct Rule{};
Result<struct Node, struct CompileError> parse(struct String input);
Result<struct String, struct CompileError> generate(struct Node node);
#endif
