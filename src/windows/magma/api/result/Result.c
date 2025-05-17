#include "./Result.h"
export interface Result<T, X> {
	mut findError(): Option<X>;
	mut findValue(): Option<T>;
	mut match<R>(mut whenOk: (arg0 : T) => R, mut whenErr: (arg0 : X) => R): R;
}
