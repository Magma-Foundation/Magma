#include "Option.h"
magma.option.Option<magma.option.R> map(magma.option.R(*mapper)(T));
T orElseGet(T(*other)());
magma.option.Tuple<magma.option.Boolean, T> toTuple(T other);
magma.option.void ifPresent(void(*consumer)(T));
T orElse(T other);
magma.option.Option<T> filter(int(*predicate)(T));
magma.option.boolean isPresent();
magma.option.R match(magma.option.R(*ifPresent)(T), magma.option.R(*ifEmpty)());
magma.option.boolean isEmpty();
magma.option.Option<T> or(magma.option.Option<T>(*other)());
magma.option.Option<magma.option.R> flatMap(magma.option.Option<magma.option.R>(*mapper)(T));

