#include "./InvokableNode.h"
export class InvokableNode implements Value {
	caller: Caller;
	args: List<Value>;
	constructor (caller: Caller, args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
}

generate(): &[I8] {
	let joinedArguments: &[I8] = this.joinArgs();
	return this.caller.generate() + "(" + joinedArguments + ")";
}
joinArgs(): &[I8] {
	return this.args.query().map((value: Value) => value.generate()).collect(new Joiner(", ")).orElse("");
}
toValue(): Option<Value> {
	return new Some<Value>(this);
}
findChild(): Option<Value> {
	return new None<Value>();
}
resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
}
generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new Some<&[I8]>("\n\tstatic " + this.caller.generate() + ": " + structureName + " = new " + structureName + "(" + this.joinArgs() + ");");
}