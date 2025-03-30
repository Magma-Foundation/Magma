#ifndef magma_compile_rule_locate_LastLocator
#define magma_compile_rule_locate_LastLocator
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
struct LastLocator{
};
// expand Option_Integer = Option<struct Integer>
struct Option_Integer locate(struct String input, struct String infix);
#endif

