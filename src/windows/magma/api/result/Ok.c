#include "./Ok.h"
export class Ok<T, X> implements Result<T, X> {
	mut value: T;
	constructor (mut value: T) {
		this.value = value;
	}
}

mut findError(): Option<X> {
	return new None<X>();
}
mut findValue(): Option<T> {
	return new Some<T>(this.value);
}
mut match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
	return whenOk(this.value);
}