#include "./SliceType.h"
export class SliceType implements Type {
	Type type;
	constructor (Type type) {
		this.type = type;
	}
}

&[I8] generate() {
	return "&[" + this.type.generate() + "]";
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