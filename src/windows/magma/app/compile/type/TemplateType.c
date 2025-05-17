#include "./TemplateType.h"
export class TemplateType implements Type {
	base: &[I8];
	args: List<&[I8]>;
	constructor (base: &[I8], args: List<&[I8]>) {
		this.base = base;
		this.args = args;
	}
}

static generateValueStrings(values: List<&[I8]>): &[I8] {
	return Main.generateAll(values, TemplateType.mergeValues);
}
static mergeValues(cache: &[I8], element: &[I8]): &[I8] {
	if (Strings.isEmpty(cache)) {
		return cache + element;
	}
	return cache + ", " + element;
}
generate(): &[I8] {
	return this.base + "<" + TemplateType.generateValueStrings(this.args) + ">";
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