#ifndef magma_collect_stream_Joiner
#define magma_collect_stream_Joiner
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct Joiner{};
struct public Joiner();
struct Option_String createInitial();
struct Option_String fold(struct Option_String maybeCurrent, struct String element);
#endif
