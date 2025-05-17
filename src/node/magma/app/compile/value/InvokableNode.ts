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
		let joinedArguments: string = this/*auto*/.joinArgs(platform/*auto*/);
		return this/*auto*/.caller.generate(platform/*auto*/) + "(" + joinedArguments/*auto*/ + ")";
	}
	joinArgs(platform: Platform): string {
		return this/*auto*/.args.query(/*auto*/).map((value: Value) => value/*auto*/.generate(platform/*auto*/)).collect(new Joiner(", ")).orElse("");
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	resolve(state: CompileState): Type {
		return PrimitiveType/*auto*/.Auto;
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new Some<string>("\n\tstatic " + this/*auto*/.caller.generate(platform/*auto*/) + ": " + structureName/*auto*/ + " = new " + structureName/*auto*/ + "(" + this/*auto*/.joinArgs(platform/*auto*/) + ");");
	}
}
