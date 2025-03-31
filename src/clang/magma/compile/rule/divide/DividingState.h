#ifndef magma_compile_rule_divide_DividingState
#define magma_compile_rule_divide_DividingState
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct DividingState{
};
// expand magma.collect.list.List_<String>
// expand magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Option<magma.compile.rule.divide.DividingState>
// expand magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Option<char>
magma.compile.rule.divide.DividingState append(char c);
magma.compile.rule.divide.boolean isLevel();
magma.compile.rule.divide.DividingState exit();
magma.compile.rule.divide.DividingState enter();
magma.compile.rule.divide.DividingState advance();
magma.collect.list.List_<String> segments();
magma.compile.rule.divide.boolean isShallow();
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> append();
magma.option.Option<magma.compile.rule.divide.DividingState> appendAndDiscard();
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> pop();
magma.option.Option<char> peek();
#endif

