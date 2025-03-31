#ifndef magma_compile_rule_text_StripRule
#define magma_compile_rule_text_StripRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Result.h"
struct StripRule{
};
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
magma.compile.rule.text.public StripRule(magma.compile.rule.Rule childRule);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
String attachPadding(magma.compile.Node node, String value);
auto __lambda0__();
#endif

