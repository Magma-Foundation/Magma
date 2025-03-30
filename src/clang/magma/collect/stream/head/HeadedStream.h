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
// expand Stream_T = Stream<struct T>
// expand BiFunction_R_T_R = BiFunction<struct R, struct T, struct R>
// expand Stream_R = Stream<struct R>
// expand HeadedStream_ = HeadedStream<struct >
// expand Collector_T_C = Collector<struct T, struct C>
// expand Option_T = Option<struct T>
// expand Option_R = Option<struct R>
// expand BiFunction_R_T_R = BiFunction<struct R, struct T, struct R>
// expand Stream_T = Stream<struct T>
// expand HeadedStream_ = HeadedStream<struct >
// expand SingleHead_ = SingleHead<struct >
// expand EmptyHead_ = EmptyHead<struct >
// expand Predicate_T = Predicate<struct T>
// expand Stream_R = Stream<struct R>
// expand Stream_R = Stream<struct R>
// expand HeadedStream_ = HeadedStream<struct >
// expand EmptyHead_ = EmptyHead<struct >
// expand Stream_R = Stream<struct R>
// expand magma_result_Result_R_X = magma.result.Result<struct R, struct X>
// expand Result_R_X = Result<struct R, struct X>
// expand Ok_ = Ok<struct >
// expand BiFunction_R_T_Result_R_X = BiFunction<struct R, struct T, struct Result_R_X>
// expand Result_R_X = Result<struct R, struct X>
// expand Stream_T = Stream<struct T>
// expand HeadedStream_ = HeadedStream<struct >
// expand Stream_T = Stream<struct T>
// expand Head_T = Head<struct T>
struct R foldWithInitial(struct R initial, struct BiFunction_R_T_R folder);
struct Stream_R map(struct R(*mapper)(struct T));
struct C collect(struct Collector_T_C collector);
struct Option_T next();
struct Option_R foldMapping(struct R(*mapper)(struct T), struct BiFunction_R_T_R folder);
struct Stream_T filter(struct Predicate_T predicate);
struct Stream_R flatMap(struct Stream_R(*mapper)(struct T));
struct magma_result_Result_R_X foldToResult(struct R initial, struct BiFunction_R_T_Result_R_X folder);
struct Stream_T concat(struct Stream_T other);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda5__();
auto __lambda6__();
#endif

