#ifndef magma_compile_rule_divide_CharDivider
#define magma_compile_rule_divide_CharDivider
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/collect/list/List_.h"
struct CharDivider{};
// expand List__String = List_<struct String>
struct List__String divide(struct String input);
struct String join(struct String current, struct String element);
#endif
