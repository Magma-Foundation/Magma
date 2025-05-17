import { Value } from "../../../../magma/app/compile/value/Value";
import { Platform } from "../../../../magma/app/io/Platform";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
import { Type } from "../../../../magma/api/Type";
import { CompileState } from "../../../../magma/app/compile/CompileState";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class StringNode implements Value {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(platform: Platform): string {
		return "\"" + this.value + "\"";
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
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>();
	}
}
