#ifndef magma_compile_rule_divide_DecoratedFolder
#define magma_compile_rule_divide_DecoratedFolder
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
#include "../../../../magma/option/Tuple.h"
struct DecoratedFolder{
};
// expand Option<DividingState>
// expand Tuple<char, DividingState>
Option<DividingState> processSlash(Tuple<char, DividingState> tuple);
DividingState fold(DividingState state, char c);
String join(String current, String element);
#endif
