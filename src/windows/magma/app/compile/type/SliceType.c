#include "./SliceType.h"
export class SliceType implements Type {
	Type type;
	constructor (Type type) {
		this.type = type;
	}
}

&[I8] generate() {
	return "&[" + this/*auto*/.type.generate(/*auto*/) + "]";
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