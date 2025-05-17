#include "./Primitive.h"
export class Primitive implements Type {
	static String: Primitive = new Primitive("string");
	static Number: Primitive = new Primitive("number");
	static Var: Primitive = new Primitive("var");
	static Void: Primitive = new Primitive("void");
	static Unknown: Primitive = new Primitive("unknown");
	static I8: Primitive = new Primitive("I8");
	static I32: Primitive = new Primitive("I32");
	value: &[I8];
	constructor (value: &[I8]) {
		this.value = value;
	}
	mut generate(): &[I8] {
		return this.value;
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return Primitive.Var === this;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
}
