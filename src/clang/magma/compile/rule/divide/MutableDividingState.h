#ifndef magma_compile_rule_divide_MutableDividingState
#define magma_compile_rule_divide_MutableDividingState
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct MutableDividingState{List_<struct Character> queueList_<struct String> segmentsstruct StringBuilder bufferstruct int depth};
struct public MutableDividingState(List_<struct Character> queue);
struct public MutableDividingState(List_<struct Character> queue, List_<struct String> segments, struct StringBuilder buffer, struct int depth);
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
