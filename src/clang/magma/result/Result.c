#include "Result.h"
struct Option_T findValue();
struct Option_X findError();
struct Result_R_X mapValue(struct R(*mapper)(struct T));
struct Result_R_X flatMapValue(struct Result_R_X(*mapper)(struct T));
struct Result_T_R mapErr(struct R(*mapper)(struct X));
struct R match(struct R(*whenOk)(struct T), struct R(*whenErr)(struct X));
struct Result_Tuple_T_R_X and(struct Supplier_Result_R_X other);

