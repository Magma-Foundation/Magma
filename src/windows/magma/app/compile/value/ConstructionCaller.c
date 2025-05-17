#include "./ConstructionCaller.h"
export class ConstructionCaller implements Caller {
	right: &[I8];
	platform: Platform;
	constructor (right: &[I8], platform: Platform) {
		this.right = right;
		this.platform = platform;
	}
}

generate(): &[I8] {
	if (Platform.Magma === this.platform) {
		return this.right;
	}
	return "new " + this.right;
}
findChild(): Option<Value> {
	return new None<Value>();
}