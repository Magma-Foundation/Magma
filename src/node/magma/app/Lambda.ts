import { Value } from "../../magma/app/Value";
import { Definition } from "../../magma/app/Definition";
import { List } from "../../magma/api/collect/list/List";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Option } from "../../magma/api/option/Option";
import { Some } from "../../magma/api/option/Some";
import { None } from "../../magma/api/option/None";
import { Type } from "../../magma/api/Type";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Primitive } from "../../magma/app/Primitive";
class Lambda implements Value {
	paramNames: List<Definition>;
	content: string;
	constructor (paramNames: List<Definition>, content: string) {
		this.paramNames = paramNames;
		this.content = content;
	}
	generate(): string {
		let joinedParamNames = this.paramNames.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames + ")" + " => " + this.content;
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
		return new None<string>();
	}
}
