#ifndef magma_compile_rule_text_InfixRule
#define magma_compile_rule_text_InfixRule
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/compile/Node.h"
#include "../../../../magma/compile/context/StringContext.h"
#include "../../../../magma/compile/rule/Rule.h"
#include "../../../../magma/compile/rule/locate/Locator.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Result.h"
struct InfixRule{struct Rule leftstruct String infixstruct Rule rightstruct Locator locator};
struct public InfixRule(struct Rule left, struct String infix, struct Rule right, struct Locator locator);
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node node);
#endif
