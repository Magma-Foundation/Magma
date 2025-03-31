#ifndef magma_collect_set_Set_
#define magma_collect_set_Set_
#include "../../../magma/collect/stream/Stream.h"
struct Set_<T>{
};
// expand magma.collect.stream.Stream<T>
// expand magma.collect.set.Set_<T>
magma.collect.stream.Stream<T> stream();
magma.collect.set.Set_<T> add(T element);
magma.collect.set.void forEach(void(*consumer)(T));
#endif

