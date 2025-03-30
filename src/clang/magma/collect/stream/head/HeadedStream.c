#include "/magma/collect/stream/Collector.h"
#include "/magma/collect/stream/Stream.h"
#include "/magma/option/Option.h"
#include "/magma/option/Tuple.h"
#include "/magma/result/Ok.h"
#include "/magma/result/Result.h"
#include "/java/util/function/BiFunction.h"
#include "/java/util/function/Function.h"
#include "/java/util/function/Predicate.h"
struct HeadedStream{};
R foldWithInitial(R initial, struct BiFunction_R_T_R folder){
}
struct Stream_R map(struct Function_T_R mapper){
}
C collect(struct Collector_T_C collector){
}
struct Option_T next(){
}
struct Option_R foldMapping(struct Function_T_R mapper, struct BiFunction_R_T_R folder){
}
struct Stream_T filter(struct Predicate_T predicate){
}
struct Stream_R flatMap(struct Function_T_Stream_R mapper){
}
struct magma_result_Result_R_X foldToResult(R initial, struct BiFunction_R_T_Result_R_X folder){
}
struct Stream_T concat(struct Stream_T other){
}
