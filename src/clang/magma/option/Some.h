#ifndef magma_option_Some
#define magma_option_Some
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
// expand magma.option.Option<T>
// expand magma.option.Option<magma.option.R>
// expand magma.option.Some<>
// expand magma.option.Tuple<magma.option.Boolean, T>
// expand magma.option.Tuple<>
// expand magma.option.Option<T>
// expand magma.option.None<>
// expand magma.option.Option<T>
// expand magma.option.Option<T>
// expand magma.option.Option<magma.option.R>
// expand magma.option.Option<magma.option.R>
#endif

