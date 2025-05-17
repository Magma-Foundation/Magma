#include "./TemplateType.h"
export class TemplateType implements Type {
	&[I8] base;
	List<&[I8]> args;
	constructor (&[I8] base, List<&[I8]> args) {
		this.base = base;
		this.args = args;
	}
}

static &[I8] generateValueStrings(List<&[I8]> values) {
	return Main.generateAll(values, TemplateType.mergeValues);
}
static &[I8] mergeValues(&[I8] cache, &[I8] element) {
	if (Strings.isEmpty(cache)) {
		return cache + element;
	}
	return cache + ", " + element;
}
&[I8] generate() {
	return this.base + "<" + TemplateType.generateValueStrings(this.args) + ">";
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