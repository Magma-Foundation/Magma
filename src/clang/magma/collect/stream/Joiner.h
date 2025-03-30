#ifndef magma_collect_stream_Joiner
#define magma_collect_stream_Joiner
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct Joiner{
};
// expand Collector_String_Option_String = Collector<struct String, struct Option_String>
// expand Option_String = Option<struct String>
// expand Option_String = Option<struct String>
// expand None_ = None<struct >
// expand Option_String = Option<struct String>
// expand Some_ = Some<struct >
// expand Option_String = Option<struct String>
struct public Joiner();
struct Option_String createInitial();
struct Option_String fold(struct Option_String maybeCurrent, struct String element);
auto __lambda0__();
#endif

