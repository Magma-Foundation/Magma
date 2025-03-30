#ifndef magma_compile_rule_locate_Locator
#define magma_compile_rule_locate_Locator
#include "../../../../magma/option/Option.h"
struct Locator{
};
// expand Option_Integer = Option<struct Integer>
struct Option_Integer locate(struct String input, struct String infix);
#endif

