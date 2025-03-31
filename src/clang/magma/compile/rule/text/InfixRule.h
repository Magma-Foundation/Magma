#ifndef magma_compile_rule_text_InfixRule
#define magma_compile_rule_text_InfixRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/compile/rule/locate/Locator.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct InfixRule{magma.compile.rule.Rule left;String infix;magma.compile.rule.Rule right;magma.compile.rule.locate.Locator locator;
};
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
magma.compile.rule.text.public InfixRule(magma.compile.rule.Rule left, String infix, magma.compile.rule.Rule right, magma.compile.rule.locate.Locator locator);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
auto __lambda0__();
auto __lambda1__();
#endif

