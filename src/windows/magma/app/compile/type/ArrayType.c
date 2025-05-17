#include "./ArrayType.h"
export class ArrayType implements Type {
	child: Type;
	constructor (child: Type) {
		this.child = child;
	}
}

generate(): &[I8] {
	return this.child.generate() + "[]";
}
isFunctional(): Bool {
	return false;
}
isVar(): Bool {
	return false;
}
generateBeforeName(): &[I8] {
	return "";
}