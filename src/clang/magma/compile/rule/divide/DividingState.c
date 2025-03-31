#include "DividingState.h"
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
