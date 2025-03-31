#ifndef magma_compile_rule_divide_MutableDividingState
#define magma_compile_rule_divide_MutableDividingState
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
struct MutableDividingState{magma.collect.list.List_<char> queue;magma.collect.list.List_<String> segments;magma.compile.rule.divide.StringBuilder buffer;int depth;
};
// expand magma.collect.list.List_<char>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<char>
// expand magma.collect.list.List_<char>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Tuple<char, magma.compile.rule.divide.DividingState>
// expand magma.option.Option<char>
// expand magma.option.Option<magma.compile.rule.divide.DividingState>
auto __lambda0__();
auto __lambda1__();
magma.compile.rule.divide.public MutableDividingState(magma.collect.list.List_<char> queue);
magma.compile.rule.divide.public MutableDividingState(magma.collect.list.List_<char> queue, magma.collect.list.List_<String> segments, magma.compile.rule.divide.StringBuilder buffer, int depth);
magma.compile.rule.divide.DividingState append(char c);
int isLevel();
magma.compile.rule.divide.DividingState exit();
magma.compile.rule.divide.DividingState enter();
magma.compile.rule.divide.DividingState advance();
magma.collect.list.List_<String> segments();
int isShallow();
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> append();
magma.option.Option<magma.option.Tuple<char, magma.compile.rule.divide.DividingState>> pop();
magma.option.Option<char> peek();
magma.option.Option<magma.compile.rule.divide.DividingState> appendAndDiscard();
#endif
