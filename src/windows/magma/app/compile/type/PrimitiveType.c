#include "./PrimitiveType.h"
export class PrimitiveType implements Type {
	static String: PrimitiveType = new PrimitiveType("string");
	static Number: PrimitiveType = new PrimitiveType("number");
	static Var: PrimitiveType = new PrimitiveType("var");
	static Void: PrimitiveType = new PrimitiveType("void");
	static Unknown: PrimitiveType = new PrimitiveType("unknown");
	static I8: PrimitiveType = new PrimitiveType("I8");
	static I32: PrimitiveType = new PrimitiveType("I32");
	value: &[I8];
}

constructor (value: &[I8]) {
	this.value = value;
}
generate(): &[I8] {
	return this.value;
}
isFunctional(): Bool {
	return false;
}
isVar(): Bool {
	return PrimitiveType.Var === this;
}
generateBeforeName(): &[I8] {
	return "";
}