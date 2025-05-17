#include "./Err.h"
export class Err<T, X> implements Result<T, X> {
	X error;
	constructor (X error) {
		this.error = error;
	}
}

Option<X> findError() {
	return new Some<X>(this/*auto*/.error);
}
Option<T> findValue() {
	return new None<T>(/*auto*/);
}
R match((arg0 : T) => R whenOk, (arg0 : X) => R whenErr) {
	return whenErr/*(arg0 : X) => R*/(this/*auto*/.error);
}
Result<R, X> flatMapValue((arg0 : T) => Result<R, X> mapper) {
	return new Err<>(this/*auto*/.error);
}
Result<R, X> mapValue((arg0 : T) => R mapper) {
	return new Err<>(this/*auto*/.error);
}