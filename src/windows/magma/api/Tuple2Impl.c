#include "./Tuple2Impl.h"
export class Tuple2Impl<A, B> implements Tuple2<A, B> {
	A leftValue;
	B rightValue;
	constructor (A leftValue, B rightValue) {
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}
}

A left() {
	return this.leftValue;
}
B right() {
	return this.rightValue;
}