#ifndef magma_compile_rule_divide_DividingState
#define magma_compile_rule_divide_DividingState
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct DividingState{
};
// expand List__String = List_<struct String>
// expand Option_Tuple_Character_DividingState = Option<struct Tuple_Character_DividingState>
// expand Tuple_Character_DividingState = Tuple<struct Character, struct DividingState>
// expand Option_DividingState = Option<struct DividingState>
struct DividingState append(struct char c);
int isLevel();
struct DividingState exit();
struct DividingState enter();
struct DividingState advance();
struct List__String segments();
int isShallow();
struct Option_Tuple_Character_DividingState append();
struct Option_DividingState appendAndDiscard();
#endif

