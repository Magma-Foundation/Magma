#ifndef magma_collect_stream_head_RangeHead
#define magma_collect_stream_head_RangeHead
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
struct RangeHead{int extent;int counter;
};
magma.collect.stream.head.public RangeHead(int extent);
magma.option.Option<int> next();
// expand magma.option.Option<int>
// expand magma.option.Some<>
#endif

