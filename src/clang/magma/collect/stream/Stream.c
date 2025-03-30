#include "Stream.h"
// expand BiFunction_R_T_R = BiFunction<struct R, struct T, struct R>
// expand Stream_R = Stream<struct R>
// expand Collector_T_C = Collector<struct T, struct C>
// expand Stream_R = Stream<struct R>
// expand Stream_R = Stream<struct R>
// expand Result_R_X = Result<struct R, struct X>
// expand BiFunction_R_T_Result_R_X = BiFunction<struct R, struct T, struct Result_R_X>
// expand Result_R_X = Result<struct R, struct X>
// expand Option_R = Option<struct R>
// expand BiFunction_R_T_R = BiFunction<struct R, struct T, struct R>
// expand Stream_T = Stream<struct T>
// expand Predicate_T = Predicate<struct T>
// expand Stream_T = Stream<struct T>
// expand Stream_T = Stream<struct T>
// expand Option_T = Option<struct T>
struct R foldWithInitial(struct R initial, struct BiFunction_R_T_R folder);
struct Stream_R map(struct R(*mapper)(struct T));
struct C collect(struct Collector_T_C collector);
struct Stream_R flatMap(struct Stream_R(*mapper)(struct T));
struct Result_R_X foldToResult(struct R initial, struct BiFunction_R_T_Result_R_X folder);
struct Option_R foldMapping(struct R(*mapper)(struct T), struct BiFunction_R_T_R folder);
struct Stream_T filter(struct Predicate_T predicate);
struct Stream_T concat(struct Stream_T other);
struct Option_T next();
