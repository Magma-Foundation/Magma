#ifndef magma_compile_lang_FilterRule
#define magma_compile_lang_FilterRule
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/StringContext.h"
#include "../../../magma/compile/rule/Rule.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Result.h"
struct FilterRule{struct Rule rulestruct Filter filter
};
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct public FilterRule(struct Filter filter, struct Rule rule);
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node node);
#endif

