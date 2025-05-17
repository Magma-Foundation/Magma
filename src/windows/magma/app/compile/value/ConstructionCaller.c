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
	if (Platform.Magma === this.platform) {
		return this.right;
	}
	return "new " + this.right;
}
Option<Value> findChild() {
	return new None<Value>();
}