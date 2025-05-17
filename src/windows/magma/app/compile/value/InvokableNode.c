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
	&[I8] joinedArguments = this/*auto*/.joinArgs(platform/*auto*/);
	return this/*auto*/.caller.generate(platform/*auto*/) + "(" + joinedArguments/*auto*/ + ")";
}
auto temp(Value value) {
	return value/*auto*/.generate(platform/*auto*/);
}
&[I8] joinArgs(Platform platform) {
	return this/*auto*/.args.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Type resolve(CompileState state) {
	return PrimitiveType/*auto*/.Auto;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new Some<&[I8]>("\n\tstatic " + this/*auto*/.caller.generate(platform/*auto*/) + ": " + structureName/*auto*/ + " = new " + structureName/*auto*/ + "(" + this/*auto*/.joinArgs(platform/*auto*/) + ");");
}