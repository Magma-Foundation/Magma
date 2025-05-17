#include "./Tuple2Impl.h"
export class Tuple2Impl<A, B> implements Tuple2<A, B> {
	leftValue: A;
	rightValue: B;
	constructor (leftValue: A, rightValue: B) {
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}
}

left(): A {
	return this.leftValue;
}
right(): B {
	return this.rightValue;
}