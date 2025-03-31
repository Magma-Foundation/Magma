#include "List_.h"
magma.collect.stream.Stream<T> stream();
magma.collect.list.List_<T> add(T element);
magma.collect.list.List_<T> addAll(magma.collect.list.List_<T> others);
magma.option.Option<T> findFirst();
int size();
magma.collect.list.List_<T> subList(int start, int end);
int equalsTo(magma.collect.list.List_<T> other);
magma.collect.list.List_<T> sort(int(*comparator)(T, T));
T get(int index);
magma.option.Option<magma.option.Tuple<T, magma.collect.list.List_<T>>> popFirst();
int isEmpty();
magma.collect.list.List_<T> clear();
magma.option.Option<T> findLast();
magma.collect.list.void forEach(void(*consumer)(T));
magma.collect.list.List_<T> mapLast(T(*mapper)(T));

