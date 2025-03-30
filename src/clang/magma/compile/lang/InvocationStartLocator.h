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
struct InvocationStartLocator{struct Tuple_Integer_Character DEFAULT_PAIR
};
// expand Tuple_Integer_Character = Tuple<struct Integer, struct Character>
// expand List__Tuple_Integer_Character = List_<struct Tuple_Integer_Character>
// expand Tuple_Integer_Character = Tuple<struct Integer, struct Character>
// expand List__Tuple_Integer_Character = List_<struct Tuple_Integer_Character>
// expand Tuple_Integer_Character = Tuple<struct Integer, struct Character>
// expand Option_Integer = Option<struct Integer>
// expand None_ = None<struct >
struct List__Tuple_Integer_Character skipDoubleQuotes(struct List__Tuple_Integer_Character queue);
struct Option_Integer locate(struct String input, struct String infix);
#endif

