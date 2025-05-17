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
	return Main/*auto*/.generateAll(values/*List<&[I8]>*/, TemplateType/*auto*/.mergeValues);
}
static &[I8] mergeValues(&[I8] cache, &[I8] element) {
	if (Strings/*auto*/.isEmpty(cache/*&[I8]*/)) {
		return cache/*&[I8]*/ + element/*&[I8]*/;
	}
	return cache/*&[I8]*/ + ", " + element/*&[I8]*/;
}
&[I8] generate() {
	return this/*auto*/.base + "<" + TemplateType/*auto*/.generateValueStrings(this/*auto*/.args) + ">";
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