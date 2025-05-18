#include "./BooleanType.h"
export class BooleanType implements Type {
	Platform platform;
	constructor (Platform platform) {
		this.platform = platform;
	}
}

&[I8] generate() {
	if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
		return "boolean";
	}
	return "Bool";
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