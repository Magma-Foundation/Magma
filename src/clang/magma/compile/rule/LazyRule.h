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
struct LazyRule{Option<struct Rule> child};
Result<struct Node, struct CompileError> parse(struct String input);
Result<struct String, struct CompileError> generate(struct Node node);
Result<struct T, struct CompileError> withChildSet(Function<struct Rule, Result<struct T, struct CompileError>> mapper, struct Context context);
struct void set(struct Rule child);
#endif
