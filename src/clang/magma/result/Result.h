#ifndef magma_result_Result
#define magma_result_Result
#include "../../magma/option/Option.h"
#include "../../magma/option/Tuple.h"
struct Result<T, X>{
};
// expand Option_T = Option<struct T>
// expand Option_X = Option<struct X>
// expand Result_R_X = Result<struct R, struct X>
// expand Result_R_X = Result<struct R, struct X>
// expand Result_R_X = Result<struct R, struct X>
// expand Result_T_R = Result<struct T, struct R>
// expand Result_Tuple_T_R_X = Result<struct Tuple_T_R, struct X>
// expand Tuple_T_R = Tuple<struct T, struct R>
// expand Result_R_X = Result<struct R, struct X>
struct Option_T findValue();
struct Option_X findError();
struct Result_R_X mapValue(struct R(*mapper)(struct T));
struct Result_R_X flatMapValue(struct Result_R_X(*mapper)(struct T));
struct Result_T_R mapErr(struct R(*mapper)(struct X));
struct R match(struct R(*whenOk)(struct T), struct R(*whenErr)(struct X));
struct Result_Tuple_T_R_X and(struct Result_R_X(*other)());
#endif

