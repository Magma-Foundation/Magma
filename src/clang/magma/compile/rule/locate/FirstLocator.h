#ifndef magma_compile_rule_locate_FirstLocator
#define magma_compile_rule_locate_FirstLocator
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
struct FirstLocator{};
Option<struct Integer> locate(struct String input, struct String infix);
#endif
