#ifndef magma_compile_lang_FilterRule
#define magma_compile_lang_FilterRule
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/StringContext.h"
#include "../../../magma/compile/rule/Rule.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Result.h"
struct FilterRule{magma.compile.rule.Rule rule;magma.compile.lang.Filter filter;
};
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
magma.compile.lang.public FilterRule(magma.compile.lang.Filter filter, magma.compile.rule.Rule rule);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
#endif

