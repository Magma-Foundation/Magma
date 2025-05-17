#include "./PrimitiveType.h"
export class PrimitiveType implements Type {
	static String: PrimitiveType = new PrimitiveType("string");
	static Number: PrimitiveType = new PrimitiveType("number");
	static Var: PrimitiveType = new PrimitiveType("var");
	static Void: PrimitiveType = new PrimitiveType("void");
	static Unknown: PrimitiveType = new PrimitiveType("unknown");
	static I8: PrimitiveType = new PrimitiveType("I8");
	static I32: PrimitiveType = new PrimitiveType("I32");
	&[I8] value;
}

constructor (&[I8] value) {
	this.value = value;
}
&[I8] generate() {
	return this.value;
}
Bool isFunctional() {
	return false;
}
Bool isVar() {
	return PrimitiveType.Var === this;
}
&[I8] generateBeforeName() {
	return "";
}