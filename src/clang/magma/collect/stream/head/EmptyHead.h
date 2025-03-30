#ifndef magma_collect_stream_head_EmptyHead
#define magma_collect_stream_head_EmptyHead
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
struct EmptyHead<T>{
};
// expand Option_T = Option<struct T>
// expand None_ = None<struct >
struct Option_T next();
#endif

