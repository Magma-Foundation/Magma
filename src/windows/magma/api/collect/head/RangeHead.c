#include "./RangeHead.h"
export class RangeHead implements Head<number> {
	number length;
	number counter;
}

constructor (number length) {
	this.length = length;
	this.counter = 0;
}
Option<number> next() {
	if (this.counter >= this.length) {
		return new None<number>();
	}
	number value = this.counter;
	this.counter++;
	return new Some<number>(value);
}