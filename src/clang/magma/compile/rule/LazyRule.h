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
struct LazyRule{Option<Rule> child;
};
// expand Option<Rule>
// expand Result<Node, CompileError>
// expand Result<String, CompileError>
// expand Result<T, CompileError>
// expand Result<T, CompileError>
// expand Err<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
Result<Node, CompileError> parse(String input);
Result<String, CompileError> generate(Node node);
Result<T, CompileError> withChildSet(Result<T, CompileError>(*mapper)(Rule), Context context);
void set(Rule child);
#endif
