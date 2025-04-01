#ifndef magma_compile_rule_divide_DividingState
#define magma_compile_rule_divide_DividingState
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct DividingState{
};
// expand List_<String>
// expand Option<Tuple<char, DividingState>>
// expand Tuple<char, DividingState>
// expand Tuple<char, DividingState>
// expand Option<DividingState>
// expand Option<Tuple<char, DividingState>>
// expand Tuple<char, DividingState>
// expand Tuple<char, DividingState>
// expand Option<char>
DividingState append(char c);
int isLevel();
DividingState exit();
DividingState enter();
DividingState advance();
List_<String> segments();
int isShallow();
Option<Tuple<char, DividingState>> append();
Option<DividingState> appendAndDiscard();
Option<Tuple<char, DividingState>> pop();
Option<char> peek();
#endif
