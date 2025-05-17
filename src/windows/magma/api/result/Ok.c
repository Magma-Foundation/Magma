#include "./Ok.h"
export class Ok<T, X> implements Result<T, X> {
	T value;
	constructor (T value) {
		this.value = value;
	}
}

Option<X> findError() {
	return new None<X>(/*auto*/);
}
Option<T> findValue() {
	return new Some<T>(this/*auto*/.value);
}
R match((arg0 : T) => R whenOk, (arg0 : X) => R whenErr) {
	return whenOk/*(arg0 : T) => R*/(this/*auto*/.value);
}
Result<R, X> flatMapValue((arg0 : T) => Result<R, X> mapper) {
	return mapper/*(arg0 : T) => Result<R, X>*/(this/*auto*/.value);
}