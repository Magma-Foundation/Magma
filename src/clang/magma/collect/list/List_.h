#ifndef magma_collect_list_List_
#define magma_collect_list_List_
#include "../../../magma/collect/stream/Stream.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Tuple.h"
struct List_{};
struct Stream_T stream();
struct List__T add(struct T element);
struct List__T addAll(struct List__T others);
struct Option_T findFirst();
struct int size();
struct List__T subList(struct int start, struct int end);
int equalsTo(struct List__T other);
struct List__T sort(struct BiFunction_T_T_Integer comparator);
struct T get(struct int index);
struct Option_Tuple_T_List__T popFirst();
int isEmpty();
#endif
