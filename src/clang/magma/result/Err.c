#include "Err.h"
struct Option_T findValue(){return None_();
}
struct Option_X findError(){return Some_(error);
}
struct Result_R_X mapValue(struct R(*mapper)(struct T)){return Err_(error);
}
struct Result_R_X flatMapValue(struct Result_R_X(*mapper)(struct T)){return Err_(error);
}
struct Result_T_R mapErr(struct R(*mapper)(struct X)){return Err_(mapper.apply(error));
}
struct R match(struct R(*whenOk)(struct T), struct R(*whenErr)(struct X)){return whenErr.apply(error);
}
struct Result_Tuple_T_R_X and(struct Result_R_X(*other)()){return Err_(error);
}

