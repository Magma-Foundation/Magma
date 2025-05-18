#include "./ArrayType.h"
export class ArrayType implements Type {
	Type child;
	constructor (Type child) {
		this.child = child;
	}
}

&[I8] generate() {
	return this/*auto*/.child.generate(/*auto*/) + "[]";
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}