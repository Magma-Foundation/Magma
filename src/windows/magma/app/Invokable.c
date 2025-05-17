#include "./Invokable.h"
class Invokable implements Value {
	mut caller: Caller;
	mut args: List<Value>;
	constructor (mut caller: Caller, mut args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
	mut generate(): &[I8] {
		let joinedArguments = this.joinArgs();
		return this.caller.generate() + "(" + joinedArguments + ")";
	}
	mut joinArgs(): &[I8] {
		return this.args.query().map((mut value: Value) => value.generate()).collect(new Joiner(", ")).orElse("");
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new Some<&[I8]>("\n\tstatic " + this.caller.generate() + ": " + structureName + " = new " + structureName + "(" + this.joinArgs() + ");");
	}
}
