#ifndef magma_collect_stream_Joiner
#define magma_collect_stream_Joiner
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct Joiner{
};
// expand Collector<String, Option<String>>
// expand Option<String>
// expand Option<String>
// expand Option<String>
// expand None<>
// expand Option<String>
// expand Some<>
// expand Option<String>
int __lambda0__();
public Joiner();
Option<String> createInitial();
Option<String> fold(Option<String> maybeCurrent, String element);
#endif
