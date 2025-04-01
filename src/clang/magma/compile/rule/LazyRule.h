#ifndef magma_compile_rule_LazyRule
#define magma_compile_rule_LazyRule
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/Context.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/compile/context/StringContext.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Result.h"
struct LazyRule{magma.option.Option<magma.compile.rule.Rule> child;
};
// expand magma.option.Option<magma.compile.rule.Rule>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<String, magma.compile.CompileError>
// expand magma.result.Result<magma.compile.rule.T, magma.compile.CompileError>
// expand magma.result.Result<magma.compile.rule.T, magma.compile.CompileError>
// expand magma.result.Err<>
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input);
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node);
magma.result.Result<magma.compile.rule.T, magma.compile.CompileError> withChildSet(magma.result.Result<magma.compile.rule.T, magma.compile.CompileError>(*mapper)(magma.compile.rule.Rule), magma.compile.context.Context context);
magma.compile.rule.void set(magma.compile.rule.Rule child);
#endif
