#include "./Slice.h"
class Slice implements Type {
	mut type: Type;
	constructor (mut type: Type) {
		this.type = type;
	}
	mut generate(): &[I8] {
		return "&[" + this.type.generate() + "]";
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
