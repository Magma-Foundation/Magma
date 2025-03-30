R foldWithInitial(R initial, struct BiFunction_R_T_R folder){
}
struct Stream_R map(struct Function_T_R mapper){
}
C collect(struct Collector_T_C collector){
}
struct Stream_R flatMap(struct Function_T_Stream_R mapper){
}
struct Result_R_X foldToResult(R initial, struct BiFunction_R_T_Result_R_X folder){
}
struct Option_R foldMapping(struct Function_T_R mapper, struct BiFunction_R_T_R folder){
}
struct Stream_T filter(struct Predicate_T predicate){
}
struct Stream_T concat(struct Stream_T other){
}
struct Option_T next(){
}
