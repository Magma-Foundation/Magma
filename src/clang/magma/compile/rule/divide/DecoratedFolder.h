#ifndef magma_compile_rule_divide_DecoratedFolder
#define magma_compile_rule_divide_DecoratedFolder
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
#include "../../../../magma/option/Tuple.h"
struct DecoratedFolder{
};
// expand magma.option.Option<magma.compile.rule.divide.DividingState>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
magma.option.Option<magma.compile.rule.divide.DividingState> processSlash(magma.option.Tuple<char, magma.compile.rule.divide.DividingState> tuple);
magma.compile.rule.divide.DividingState fold(magma.compile.rule.divide.DividingState state, char c);
String join(String current, String element);
#endif
