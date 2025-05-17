#include "./Result.h"
export interface Result<T, X> {Option<X> findError();Option<T> findValue();R match((arg0 : T) => R whenOk, (arg0 : X) => R whenErr);
}
