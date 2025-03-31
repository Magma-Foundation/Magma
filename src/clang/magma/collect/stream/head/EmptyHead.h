#ifndef magma_collect_stream_head_EmptyHead
#define magma_collect_stream_head_EmptyHead
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
struct EmptyHead<T>{
};
// expand magma.option.Option<T>
// expand magma.option.None<>
magma.option.Option<T> next();
#endif

