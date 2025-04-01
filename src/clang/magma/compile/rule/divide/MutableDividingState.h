#ifndef magma_compile_rule_divide_MutableDividingState
#define magma_compile_rule_divide_MutableDividingState
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct MutableDividingState{List_<char> queue;List_<String> segments;StringBuilder buffer;int depth;
};
// expand List_<char>
// expand List_<String>
// expand List_<char>
// expand List_<char>
// expand List_<String>
// expand List_<String>
// expand Option<Tuple<char, DividingState>>
// expand Tuple<char, DividingState>
// expand Tuple<char, DividingState>
// expand Option<Tuple<char, DividingState>>
// expand Tuple<char, DividingState>
// expand Tuple<char, DividingState>
// expand Option<char>
// expand Option<DividingState>
// expand Tuple<>
// expand Tuple<>
int __lambda0__();
int __lambda1__();
public MutableDividingState(List_<char> queue);
public MutableDividingState(List_<char> queue, List_<String> segments, StringBuilder buffer, int depth);
DividingState append(char c);
int isLevel();
DividingState exit();
DividingState enter();
DividingState advance();
List_<String> segments();
int isShallow();
Option<Tuple<char, DividingState>> append();
Option<Tuple<char, DividingState>> pop();
Option<char> peek();
Option<DividingState> appendAndDiscard();
#endif
