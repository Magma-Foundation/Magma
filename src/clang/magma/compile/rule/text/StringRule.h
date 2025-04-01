#ifndef magma_compile_rule_text_StringRule
#define magma_compile_rule_text_StringRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/MapNode.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/NodeContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct StringRule{
};
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Ok<>
// expand magma.result.Result<String, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
// expand magma.result.Err<>
int __lambda0__();
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String value);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node input);
#endif
