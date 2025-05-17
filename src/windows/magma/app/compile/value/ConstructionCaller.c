#include "./ConstructionCaller.h"
export class ConstructionCaller implements Caller {
	&[I8] right;
	Platform platform;
	constructor (&[I8] right, Platform platform) {
		this.right = right;
		this.platform = platform;
	}
}

&[I8] generate(Platform platform) {
	if (Platform/*auto*/.Magma === this/*auto*/.platform) {
		return this/*auto*/.right;
	}
	return "new " + this/*auto*/.right;
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}