#include "./SingleHead.h"
export class SingleHead<T> implements Head<T> {
	T element;
	Bool retrieved;
}

constructor (T element) {
	this.element = element;
	this.retrieved = false;
}
Option<T> next() {
	if (this.retrieved) {
		return new None<T>();
	}
	this.retrieved = true;
	return new Some<T>(this.element);
}