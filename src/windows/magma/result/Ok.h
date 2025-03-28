#include "../../magma/option/None.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Some.h"
struct Ok<T, X>(T value) implements Result<T, X> {
};
