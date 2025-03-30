#ifndef magma_collect_set_Set_
#define magma_collect_set_Set_
#include "../../../magma/collect/stream/Stream.h"
struct Set_{};
// expand Stream_T = Stream<struct T>
// expand Set__T = Set_<struct T>
struct Stream_T stream();
struct Set__T add(struct T element);
#endif
