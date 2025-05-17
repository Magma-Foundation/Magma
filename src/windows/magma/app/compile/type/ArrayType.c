#include "./ArrayType.h"
export class ArrayType implements Type {
	Type child;
	constructor (Type child) {
		this.child = child;
	}
}

&[I8] generate() {
	return this.child.generate() + "[]";
}
Bool isFunctional() {
	return false;
}
Bool isVar() {
	return false;
}
&[I8] generateBeforeName() {
	return "";
}