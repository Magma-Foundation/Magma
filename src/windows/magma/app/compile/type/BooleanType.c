#include "./BooleanType.h"
export class BooleanType implements Type {
	mut platform: Platform;
	constructor (mut platform: Platform) {
		this.platform = platform;
	}
	mut generate(): &[I8] {
		if (Platform.TypeScript === this.platform) {
			return "boolean";
		}
		return "Bool";
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
}
