#include "Ok.h"
expand Result_T_X
expand Option_T
expand Option_X
expand Result_R_X
expand Result_R_X
expand Result_R_X
expand Result_T_R
expand Result_Tuple_T_R_X
expand Tuple_T_R
expand Supplier_Result_R_X
expand Result_R_X
struct Option_T findValue(}{return new Some<>(value);}struct Option_X findError(}{return new None<>();}struct Result_R_X mapValue(struct R(*mapper)(struct T)}{return new Ok<>(mapper.apply(value));}struct Result_R_X flatMapValue(struct Result_R_X(*mapper)(struct T)}{return mapper.apply(value);}struct Result_T_R mapErr(struct R(*mapper)(struct X)}{return new Ok<>(value);}struct R match(struct R(*whenOk)(struct T), struct R(*whenErr)(struct X)}{return whenOk.apply(value);}struct Result_Tuple_T_R_X and(struct Supplier_Result_R_X other}{return other.get().mapValue(otherValue -> new Tuple<>(value, otherValue));}