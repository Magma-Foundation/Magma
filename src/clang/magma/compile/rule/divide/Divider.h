#ifndef magma_compile_rule_divide_Divider
#define magma_compile_rule_divide_Divider
#include "../../../../magma/collect/list/List_.h"
struct Divider{
};
// expand List__String = List_<struct String>
struct List__String divide(struct String input);
struct String join(struct String current, struct String element);
#endif

