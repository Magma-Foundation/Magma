#include "./Ok.h"
export class Ok<T, X> implements Result<T, X> {
	T value;
	constructor (T value) {
		this.value = value;
	}
}

Option<X> findError() {
	return new None<X>();
}
Option<T> findValue() {
	return new Some<T>(this.value);
}
R match((arg0 : T) => R whenOk, (arg0 : X) => R whenErr) {
	return whenOk(this.value);
}