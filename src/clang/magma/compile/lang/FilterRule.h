#ifndef magma_compile_lang_FilterRule
#define magma_compile_lang_FilterRule
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/StringContext.h"
#include "../../../magma/compile/rule/Rule.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Result.h"
struct FilterRule{Rule rule;Filter filter;
};
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
public FilterRule(Filter filter, Rule rule);
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
#endif
