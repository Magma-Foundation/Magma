#include "./Result.h"
export interface Result<T, X> {
	findError(): Option<X>;
	findValue(): Option<T>;
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R;
}
