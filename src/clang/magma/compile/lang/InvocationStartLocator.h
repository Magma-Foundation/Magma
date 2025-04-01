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
struct InvocationStartLocator{Tuple<int, char> DEFAULT_PAIR;
};
// expand Tuple<int, char>
// expand List_<Tuple<int, char>>
// expand Tuple<int, char>
// expand Tuple<int, char>
// expand List_<Tuple<int, char>>
// expand Tuple<int, char>
// expand Tuple<int, char>
// expand Option<int>
// expand None<>
List_<Tuple<int, char>> skipDoubleQuotes(List_<Tuple<int, char>> queue);
Option<int> locate(String input, String infix);
#endif
