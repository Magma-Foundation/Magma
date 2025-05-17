#include "./SliceType.h"
export class SliceType implements Type {
	type: Type;
	constructor (type: Type) {
		this.type = type;
	}
}

generate(): &[I8] {
	return "&[" + this.type.generate() + "]";
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