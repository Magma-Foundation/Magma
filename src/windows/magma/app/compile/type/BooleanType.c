#include "./BooleanType.h"
export class BooleanType implements Type {
	Platform platform;
	constructor (Platform platform) {
		this.platform = platform;
	}
}

&[I8] generate() {
	if (Platform.TypeScript === this.platform) {
		return "boolean";
	}
	return "Bool";
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