#ifndef magma_collect_stream_head_EmptyHead
#define magma_collect_stream_head_EmptyHead
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
struct EmptyHead{};
// expand Option_T = Option<struct T>
struct Option_T next();
#endif
