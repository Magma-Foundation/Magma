#include "./Err.h"
export class Err<T, X> implements Result<T, X> {
	mut error: X;
	constructor (mut error: X) {
		this.error = error;
	}
}

mut findError(): Option<X> {
	return new Some<X>(this.error);
}
mut findValue(): Option<T> {
	return new None<T>();
}
mut match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
	return whenErr(this.error);
}