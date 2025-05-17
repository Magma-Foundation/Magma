#include "./Err.h"
export class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
}

findError(): Option<X> {
	return new Some<X>(this.error);
}
findValue(): Option<T> {
	return new None<T>();
}
match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
	return whenErr(this.error);
}