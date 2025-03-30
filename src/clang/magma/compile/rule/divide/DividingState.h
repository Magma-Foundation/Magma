#ifndef magma_compile_rule_divide_DividingState
#define magma_compile_rule_divide_DividingState
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct DividingState{};
struct DividingState append(struct char c);
int isLevel();
struct DividingState exit();
struct DividingState enter();
struct DividingState advance();
List_<struct String> segments();
int isShallow();
Option<Tuple<struct Character, struct DividingState>> append();
Option<struct DividingState> appendAndDiscard();
#endif
