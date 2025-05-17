#include "./ArrayType.h"
export class ArrayType implements Type {
	mut child: Type;
	constructor (mut child: Type) {
		this.child = child;
	}
}

mut generate(): &[I8] {
	return this.child.generate() + "[]";
}
mut isFunctional(): Bool {
	return false;
}
mut isVar(): Bool {
	return false;
}
mut generateBeforeName(): &[I8] {
	return "";
}