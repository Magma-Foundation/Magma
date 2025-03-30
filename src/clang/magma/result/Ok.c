#include "Ok.h"
struct Option_T findValue(){return Some_(value);
}
struct Option_X findError(){return None_();
}
struct Result_R_X mapValue(struct R(*mapper)(struct T)){return Ok_(mapper.apply(value));
}
struct Result_R_X flatMapValue(struct Result_R_X(*mapper)(struct T)){return mapper.apply(value);
}
struct Result_T_R mapErr(struct R(*mapper)(struct X)){return Ok_(value);
}
struct R match(struct R(*whenOk)(struct T), struct R(*whenErr)(struct X)){return whenOk.apply(value);
}
struct Result_Tuple_T_R_X and(struct Result_R_X(*other)()){return other.get().mapValue(__lambda0__);
}
auto __lambda0__();

