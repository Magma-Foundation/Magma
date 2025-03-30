#ifndef magma_collect_stream_head_SingleHead
#define magma_collect_stream_head_SingleHead
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
struct SingleHead<T>{struct T value;int retrieved;
};
// expand Option_T = Option<struct T>
// expand Some_ = Some<struct >
struct public SingleHead(struct T value);
struct Option_T next();
#endif

