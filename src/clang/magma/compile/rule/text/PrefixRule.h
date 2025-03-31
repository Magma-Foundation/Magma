#ifndef magma_compile_rule_text_PrefixRule
#define magma_compile_rule_text_PrefixRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct PrefixRule{
};
magma.result.Result<magma.compile.Node, magma.compile.CompileError> createPrefixErr(String input, String prefix);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
auto __lambda0__();
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Err<>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
#endif

