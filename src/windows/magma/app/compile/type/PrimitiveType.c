#include "./PrimitiveType.h"
export class PrimitiveType implements Type {
	static String/*auto*/: PrimitiveType = new PrimitiveType("string");
	static Number/*auto*/: PrimitiveType = new PrimitiveType("number");
	static Var/*auto*/: PrimitiveType = new PrimitiveType("var");
	static Void/*auto*/: PrimitiveType = new PrimitiveType("void");
	static Auto/*auto*/: PrimitiveType = new PrimitiveType("auto");
	static I8/*auto*/: PrimitiveType = new PrimitiveType("I8");
	static I32/*auto*/: PrimitiveType = new PrimitiveType("I32");
	&[I8] value;
}

constructor (&[I8] value) {
	this/*auto*/.value = value/*&[I8]*/;
}
&[I8] generate() {
	return this/*auto*/.value;
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return PrimitiveType/*auto*/.Var === this/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}