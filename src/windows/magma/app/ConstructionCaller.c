#include "./ConstructionCaller.h"
class ConstructionCaller implements Caller {
	mut right: &[I8];
	mut platform: Platform;
	constructor (mut right: &[I8], mut platform: Platform) {
		this.right = right;
		this.platform = platform;
	}
	mut generate(): &[I8] {
		if (Platform.Magma === this.platform) {
			return this.right;
		}
		return "new " + this.right;
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
}
