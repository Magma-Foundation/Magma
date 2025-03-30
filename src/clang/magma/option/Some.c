#include "/java/util/function/Consumer.h"
#include "/java/util/function/Function.h"
#include "/java/util/function/Predicate.h"
#include "/java/util/function/Supplier.h"
struct Some{};
struct Option_R map(struct Function_T_R mapper){
}
T orElseGet(struct Supplier_T other){
}
struct Tuple_Boolean_T toTuple(T other){
}
void ifPresent(struct Consumer_T consumer){
}
T orElse(T other){
}
struct Option_T filter(struct Predicate_T predicate){
}
int isPresent(){
}
R match(struct Function_T_R ifPresent, struct Supplier_R ifEmpty){
}
int isEmpty(){
}
struct Option_T or(struct Supplier_Option_T other){
}
struct Option_R flatMap(struct Function_T_Option_R mapper){
}
