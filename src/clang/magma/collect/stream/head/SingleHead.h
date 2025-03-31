#ifndef magma_collect_stream_head_SingleHead
#define magma_collect_stream_head_SingleHead
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
struct SingleHead<T>{T value;magma.collect.stream.head.boolean retrieved;
};
// expand magma.option.Option<T>
// expand magma.option.Some<>
magma.collect.stream.head.public SingleHead(T value);
magma.option.Option<T> next();
#endif

