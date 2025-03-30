#ifndef magma_collect_stream_Joiner
#define magma_collect_stream_Joiner
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct Joiner{};
struct public Joiner();
Option<struct String> createInitial();
Option<struct String> fold(Option<struct String> maybeCurrent, struct String element);
#endif
