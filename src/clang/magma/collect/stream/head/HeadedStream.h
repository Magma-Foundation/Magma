#ifndef magma_collect_stream_head_HeadedStream
#define magma_collect_stream_head_HeadedStream
#include "../../../../magma/collect/stream/Collector.h"
#include "../../../../magma/collect/stream/Stream.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Tuple.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
struct HeadedStream{};
struct R foldWithInitial(struct R initial, struct BiFunction_R_T_R folder);
struct Stream_R map(struct R(*mapper)(struct T));
struct C collect(struct Collector_T_C collector);
struct Option_T next();
struct Option_R foldMapping(struct R(*mapper)(struct T), struct BiFunction_R_T_R folder);
struct Stream_T filter(struct Predicate_T predicate);
struct Stream_R flatMap(struct Stream_R(*mapper)(struct T));
struct magma_result_Result_R_X foldToResult(struct R initial, struct BiFunction_R_T_Result_R_X folder);
struct Stream_T concat(struct Stream_T other);
#endif
