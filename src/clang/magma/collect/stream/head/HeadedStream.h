#ifndef magma_collect_stream_head_HeadedStream
#define magma_collect_stream_head_HeadedStream
#include "../../../../magma/collect/stream/Collector.h"
#include "../../../../magma/collect/stream/Stream.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct HeadedStream<T>{
};
// expand magma.collect.stream.Stream<T>
// expand magma.collect.stream.head.Head<T>
// expand magma.collect.stream.Stream<magma.collect.stream.head.R>
// expand magma.collect.stream.head.HeadedStream<>
// expand magma.collect.stream.Collector<T, magma.collect.stream.head.C>
// expand magma.option.Option<T>
// expand magma.option.Option<magma.collect.stream.head.R>
// expand magma.collect.stream.Stream<T>
// expand magma.collect.stream.Stream<magma.collect.stream.head.R>
// expand magma.collect.stream.Stream<magma.collect.stream.head.R>
// expand magma.collect.stream.head.HeadedStream<>
// expand magma.collect.stream.head.EmptyHead<>
// expand magma.collect.stream.Stream<magma.collect.stream.head.R>
// expand magma.result.Result<magma.collect.stream.head.R, magma.collect.stream.head.X>
// expand magma.result.Result<magma.collect.stream.head.R, magma.collect.stream.head.X>
// expand magma.result.Ok<>
// expand magma.result.Result<magma.collect.stream.head.R, magma.collect.stream.head.X>
// expand magma.collect.stream.Stream<T>
// expand magma.collect.stream.head.HeadedStream<>
// expand magma.collect.stream.Stream<T>
magma.collect.stream.head.R foldWithInitial(magma.collect.stream.head.R initial, magma.collect.stream.head.R(*folder)(magma.collect.stream.head.R, T));
magma.collect.stream.Stream<magma.collect.stream.head.R> map(magma.collect.stream.head.R(*mapper)(T));
magma.collect.stream.head.C collect(magma.collect.stream.Collector<T, magma.collect.stream.head.C> collector);
magma.option.Option<T> next();
magma.collect.stream.head.boolean anyMatch(int(*predicate)(T));
magma.option.Option<magma.collect.stream.head.R> foldMapping(magma.collect.stream.head.R(*mapper)(T), magma.collect.stream.head.R(*folder)(magma.collect.stream.head.R, T));
magma.collect.stream.Stream<T> filter(int(*predicate)(T));
magma.collect.stream.Stream<magma.collect.stream.head.R> flatMap(magma.collect.stream.Stream<magma.collect.stream.head.R>(*mapper)(T));
magma.result.Result<magma.collect.stream.head.R, magma.collect.stream.head.X> foldToResult(magma.collect.stream.head.R initial, magma.result.Result<magma.collect.stream.head.R, magma.collect.stream.head.X>(*folder)(magma.collect.stream.head.R, T));
magma.collect.stream.Stream<T> concat(magma.collect.stream.Stream<T> other);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();
#endif

