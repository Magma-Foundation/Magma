#ifndef magma_compile_rule_divide_DividingState
#define magma_compile_rule_divide_DividingState
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct DividingState{
};
magma.compile.rule.divide.DividingState append(char c);
int isLevel();
magma.compile.rule.divide.DividingState exit();
magma.compile.rule.divide.DividingState enter();
magma.compile.rule.divide.DividingState advance();
magma.collect.list.List_<String> segments();
int isShallow();
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> append();
magma.option.Option<magma.compile.rule.divide.DividingState> appendAndDiscard();
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> pop();
magma.option.Option<char> peek();
// expand magma.collect.list.List_<String>
// expand magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Option<magma.compile.rule.divide.DividingState>
// expand magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Option<char>
#endif

