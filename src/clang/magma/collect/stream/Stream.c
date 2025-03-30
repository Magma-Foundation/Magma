#include "Stream.h"
struct R foldWithInitial(struct R initial, struct BiFunction_R_T_R folder);
struct Stream_R map(struct R(*mapper)(struct T));
struct C collect(struct Collector_T_C collector);
struct Stream_R flatMap(struct Stream_R(*mapper)(struct T));
struct Result_R_X foldToResult(struct R initial, struct BiFunction_R_T_Result_R_X folder);
struct Option_R foldMapping(struct R(*mapper)(struct T), struct BiFunction_R_T_R folder);
struct Stream_T filter(struct Predicate_T predicate);
struct Stream_T concat(struct Stream_T other);
struct Option_T next();

