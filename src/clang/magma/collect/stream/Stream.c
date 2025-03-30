#include "Stream.h"
expand BiFunction_R_T_R
expand Stream_R
expand Collector_T_C
expand Stream_R
expand Stream_R
expand Result_R_X
expand BiFunction_R_T_Result_R_X
expand Result_R_X
expand Option_R
expand BiFunction_R_T_R
expand Stream_T
expand Predicate_T
expand Stream_T
expand Stream_T
expand Option_T
struct R foldWithInitial(struct R initial, struct BiFunction_R_T_R folder);
struct Stream_R map(struct R(*mapper)(struct T));
struct C collect(struct Collector_T_C collector);
struct Stream_R flatMap(struct Stream_R(*mapper)(struct T));
struct Result_R_X foldToResult(struct R initial, struct BiFunction_R_T_Result_R_X folder);
struct Option_R foldMapping(struct R(*mapper)(struct T), struct BiFunction_R_T_R folder);
struct Stream_T filter(struct Predicate_T predicate);
struct Stream_T concat(struct Stream_T other);
struct Option_T next();
