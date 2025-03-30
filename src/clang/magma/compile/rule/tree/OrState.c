#include "OrState.h"
struct public OrState(){this(None_(), Lists.empty());
}
struct OrState_T withValue(struct T value){return OrState_(Some_(value), errors);
}
struct Result_T_List__CompileError toResult(){return maybeValue.map(Ok.new).orElseGet(__lambda0__);
}
struct OrState_T withError(struct CompileError error){return OrState_(maybeValue, errors.add(error));
}
auto __lambda0__();

