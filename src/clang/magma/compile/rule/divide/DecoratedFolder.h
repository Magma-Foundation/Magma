#ifndef magma_compile_rule_divide_DecoratedFolder
#define magma_compile_rule_divide_DecoratedFolder
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
#include "../../../../magma/option/Tuple.h"
struct DecoratedFolder{};
Option<struct DividingState> processSlash(Tuple<struct Character, struct DividingState> tuple);
struct DividingState fold(struct DividingState state, struct char c);
struct String join(struct String current, struct String element);
#endif
