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