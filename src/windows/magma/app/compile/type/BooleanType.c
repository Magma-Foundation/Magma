#include "./BooleanType.h"
export class BooleanType implements Type {
	platform: Platform;
	constructor (platform: Platform) {
		this.platform = platform;
	}
}

generate(): &[I8] {
	if (Platform.TypeScript === this.platform) {
		return "boolean";
	}
	return "Bool";
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