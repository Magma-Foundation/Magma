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
	&[I8] joinedArguments = this/*auto*/.joinArgs(platform/*Platform*/);
	return this/*auto*/.caller.generate(platform/*Platform*/) + "(" + joinedArguments/*auto*/ + ")";
}
auto temp(Value value) {
	return value/*&[I8]*/.generate(platform/*Platform*/);
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
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new Some<&[I8]>("\n\tstatic " + this/*auto*/.caller.generate(platform/*Platform*/) + ": " + structureName/*&[I8]*/ + " = new " + structureName/*&[I8]*/ + "(" + this/*auto*/.joinArgs(platform/*Platform*/) + ");");
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}