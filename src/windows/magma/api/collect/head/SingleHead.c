#include "./SingleHead.h"
export class SingleHead<T> implements Head<T> {
	T element;
	Bool retrieved;
}

constructor (T element) {
	this/*auto*/.element = element/*T*/;
	this/*auto*/.retrieved = false/*auto*/;
}
Option<T> next() {
	if (this/*auto*/.retrieved) {
		return new None<T>(/*auto*/);
	}
	this/*auto*/.retrieved = true/*auto*/;
	return new Some<T>(this/*auto*/.element);
}