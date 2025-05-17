#include "./InvokableNode.h"
export class InvokableNode implements Value {
	Caller caller;
	List<Value> args;
	constructor (Caller caller, List<Value> args) {
		this.caller = caller;
		this.args = args;
	}
}

&[I8] generate(Platform platform) {
	&[I8] joinedArguments = this.joinArgs(platform);
	return this.caller.generate(platform) + "(" + joinedArguments + ")";
}
auto temp(Value value) {value.generate(platform)
}
&[I8] joinArgs(Platform platform) {
	return this.args.query().map(temp).collect(new Joiner(", ")).orElse("");
}
Option<Value> toValue() {
	return new Some<Value>(this);
}
Option<Value> findChild() {
	return new None<Value>();
}
Type resolve(CompileState state) {
	return PrimitiveType.Auto;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new Some<&[I8]>("\n\tstatic " + this.caller.generate(platform) + ": " + structureName + " = new " + structureName + "(" + this.joinArgs(platform) + ");");
}