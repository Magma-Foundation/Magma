#ifndef magma_collect_stream_head_RangeHead
#define magma_collect_stream_head_RangeHead
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
struct RangeHead{int extent;int counter;
};
// expand Option<int>
// expand Some<>
public RangeHead(int extent);
Option<int> next();
#endif
