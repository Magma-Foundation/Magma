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
struct LazyRule{struct Option_Rule child};
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node node);
struct Result_T_CompileError withChildSet(struct Function_Rule_Result_T_CompileError mapper, struct Context context);
struct void set(struct Rule child);
#endif
