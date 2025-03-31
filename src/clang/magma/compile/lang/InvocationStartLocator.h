#ifndef magma_compile_lang_InvocationStartLocator
#define magma_compile_lang_InvocationStartLocator
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/stream/head/HeadedStream.h"
#include "../../../magma/collect/stream/head/RangeHead.h"
#include "../../../magma/compile/rule/locate/Locator.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
#include "../../../magma/option/Tuple.h"
struct InvocationStartLocator{magma.option.Tuple<int, char> DEFAULT_PAIR;
};
// expand magma.option.Tuple<int, char>
// expand magma.collect.list.List_<magma.option.Tuple<int, char>>
// expand magma.option.Tuple<int, char>
// expand magma.option.Tuple<int, char>
// expand magma.collect.list.List_<magma.option.Tuple<int, char>>
// expand magma.option.Tuple<int, char>
// expand magma.option.Tuple<int, char>
// expand magma.option.Option<int>
// expand magma.option.None<>
magma.collect.list.List_<magma.option.Tuple<int, char>> skipDoubleQuotes(magma.collect.list.List_<magma.option.Tuple<int, char>> queue);
magma.option.Option<int> locate(String input, String infix);
#endif

