#include "/magma/option/None.h"
#include "/magma/option/Option.h"
#include "/magma/option/Some.h"
#include "/magma/option/Tuple.h"
#include "/java/util/function/Function.h"
#include "/java/util/function/Supplier.h"
struct Err{};
struct Option_T findValue(){
}
struct Option_X findError(){
}
struct Result_R_X mapValue(struct Function_T_R mapper){
}
struct Result_R_X flatMapValue(struct Function_T_Result_R_X mapper){
}
struct Result_T_R mapErr(struct Function_X_R mapper){
}
R match(struct Function_T_R whenOk, struct Function_X_R whenErr){
}
struct Result_Tuple_T_R_X and(struct Supplier_Result_R_X other){
}
