#ifndef magma_option_None
#define magma_option_None
magma.option.Option<magma.option.R> map(magma.option.R(*mapper)(T));
T orElseGet(T(*other)());
magma.option.Tuple<int, T> toTuple(T other);
magma.option.void ifPresent(void(*consumer)(T));
T orElse(T other);
magma.option.Option<T> filter(int(*predicate)(T));
int isPresent();
magma.option.R match(magma.option.R(*ifPresent)(T), magma.option.R(*ifEmpty)());
int isEmpty();
magma.option.Option<T> or(magma.option.Option<T>(*other)());
magma.option.Option<magma.option.R> flatMap(magma.option.Option<magma.option.R>(*mapper)(T));
// expand magma.option.Option<magma.option.R>
// expand magma.option.None<>
// expand magma.option.Tuple<int, T>
// expand magma.option.Tuple<>
// expand magma.option.Option<T>
// expand magma.option.None<>
// expand magma.option.Option<T>
// expand magma.option.Option<T>
// expand magma.option.Option<magma.option.R>
// expand magma.option.None<>
// expand magma.option.Option<magma.option.R>
#endif

