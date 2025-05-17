#include "./RangeHead.h"
export class RangeHead implements Head<number> {
	length: number;
	mut counter: number;
}

constructor (length: number) {
	this.length = length;
	this.counter = 0;
}
mut next(): Option<number> {
	if (this.counter >= this.length) {
		return new None<number>();
	}
	let value = this.counter;
	this.counter++;
	return new Some<number>(value);
}