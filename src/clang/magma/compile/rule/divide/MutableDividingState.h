#ifndef magma_compile_rule_divide_MutableDividingState
#define magma_compile_rule_divide_MutableDividingState
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct MutableDividingState{struct List__Character queue;struct List__String segments;struct StringBuilder buffer;int depth;
};
// expand List__Character = List_<struct Character>
// expand List__String = List_<struct String>
// expand List__Character = List_<struct Character>
// expand List__Character = List_<struct Character>
// expand List__String = List_<struct String>
// expand List__String = List_<struct String>
// expand Option_Tuple_Character_DividingState = Option<struct Tuple_Character_DividingState>
// expand Tuple_Character_DividingState = Tuple<struct Character, struct DividingState>
// expand Tuple_ = Tuple<struct >
// expand Option_Tuple_Character_DividingState = Option<struct Tuple_Character_DividingState>
// expand Tuple_Character_DividingState = Tuple<struct Character, struct DividingState>
// expand Tuple_ = Tuple<struct >
// expand Option_Character = Option<struct Character>
// expand Option_DividingState = Option<struct DividingState>
struct public MutableDividingState(struct List__Character queue);
struct public MutableDividingState(struct List__Character queue, struct List__String segments, struct StringBuilder buffer, int depth);
struct DividingState append(struct char c);
int isLevel();
struct DividingState exit();
struct DividingState enter();
struct DividingState advance();
struct List__String segments();
int isShallow();
struct Option_Tuple_Character_DividingState append();
struct Option_Tuple_Character_DividingState pop();
struct Option_Character peek();
struct Option_DividingState appendAndDiscard();
auto __lambda0__();
auto __lambda1__();
#endif

