#include "./Generic.h"
class Generic implements Type {
	mut base: &[I8];
	mut args: List<&[I8]>;
	constructor (mut base: &[I8], mut args: List<&[I8]>) {
		this.base = base;
		this.args = args;
	}
	mut static generateValueStrings(values: List<&[I8]>): &[I8] {
		return Main.generateAll(values, Generic.mergeValues);
	}
	mut static mergeValues(cache: &[I8], element: &[I8]): &[I8] {
		if (Strings.isEmpty(cache)) {
			return cache + element;
		}
		return cache + ", " + element;
	}
	mut generate(): &[I8] {
		return this.base + "<" + Generic.generateValueStrings(this.args) + ">";
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
