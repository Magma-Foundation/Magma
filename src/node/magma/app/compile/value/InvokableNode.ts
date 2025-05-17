import { Value } from "../../../../magma/app/compile/value/Value";
import { Caller } from "../../../../magma/app/compile/value/Caller";
import { List } from "../../../../magma/api/collect/list/List";
import { Platform } from "../../../../magma/app/io/Platform";
import { Joiner } from "../../../../magma/api/collect/Joiner";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
import { Type } from "../../../../magma/api/Type";
import { CompileState } from "../../../../magma/app/compile/CompileState";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class InvokableNode implements Value {
	caller: Caller;
	args: List<Value>;
	constructor (caller: Caller, args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
	generate(platform: Platform): string {
		let joinedArguments: string = this.joinArgs(platform);
		return this.caller.generate(platform) + "(" + joinedArguments + ")";
	}
	joinArgs(platform: Platform): string {
		return this.args.query().map((value: Value) => value.generate(platform)).collect(new Joiner(", ")).orElse("");
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return PrimitiveType.Auto;
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new Some<string>("\n\tstatic " + this.caller.generate(platform) + ": " + structureName + " = new " + structureName + "(" + this.joinArgs(platform) + ");");
	}
}
