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
struct StringRule{};
Result<struct Node, struct CompileError> parse(struct String value);
Result<struct String, struct CompileError> generate(struct Node input);
#endif
