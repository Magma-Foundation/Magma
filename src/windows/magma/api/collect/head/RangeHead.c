#include "./RangeHead.h"
export class RangeHead implements Head<number> {
	number length;
	number counter;
}

constructor (number length) {
	this/*auto*/.length = length/*number*/;
	this/*auto*/.counter = 0/*auto*/;
}
Option<number> next() {
	if (this/*auto*/.counter >= this/*auto*/.length) {
		return new None<number>(/*auto*/);
	}
	number value = this/*auto*/.counter;
	this/*auto*/.counter++;
	return new Some<number>(value/*auto*/);
}