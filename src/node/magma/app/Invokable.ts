import { Value } from "../../magma/app/Value";
import { Caller } from "../../magma/app/compile/Caller";
import { List } from "../../magma/api/collect/list/List";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Option } from "../../magma/api/option/Option";
import { Some } from "../../magma/api/option/Some";
import { None } from "../../magma/api/option/None";
import { Type } from "../../magma/api/Type";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Primitive } from "../../magma/app/Primitive";
class Invokable implements Value {
	caller: Caller;
	args: List<Value>;
	constructor (caller: Caller, args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
	generate(): string {
		let joinedArguments = this.joinArgs();
		return this.caller.generate() + "(" + joinedArguments + ")";
	}
	joinArgs(): string {
		return this.args.query().map((value: Value) => value.generate()).collect(new Joiner(", ")).orElse("");
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new Some<string>("\n\tstatic " + this.caller.generate() + ": " + structureName + " = new " + structureName + "(" + this.joinArgs() + ");");
	}
}
