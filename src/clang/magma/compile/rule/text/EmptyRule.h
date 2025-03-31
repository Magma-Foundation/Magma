#ifndef magma_compile_rule_text_EmptyRule
#define magma_compile_rule_text_EmptyRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/MapNode.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct EmptyRule{
};
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Ok<>
// expand magma.result.Err<>
// expand magma.result.Result<String, magma.compile.CompileError>
// expand magma.result.Ok<>
#endif

