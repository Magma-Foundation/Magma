import { Value } from "../../magma/app/Value";
import { Option } from "../../magma/api/option/Option";
import { Some } from "../../magma/api/option/Some";
import { None } from "../../magma/api/option/None";
import { Type } from "../../magma/api/Type";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Primitive } from "../../magma/app/Primitive";
class StringValue implements Value {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(): string {
		return "\"" + this.value + "\"";
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
