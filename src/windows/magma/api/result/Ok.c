#include "./Ok.h"
export class Ok<T, X> implements Result<T, X> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
}

findError(): Option<X> {
	return new None<X>();
}
findValue(): Option<T> {
	return new Some<T>(this.value);
}
match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
	return whenOk(this.value);
}