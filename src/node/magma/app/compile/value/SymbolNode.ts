import { Platform } from "../../../../magma/app/io/Platform";
import { Type } from "../../../../magma/api/Type";
import { CompileState } from "../../../../magma/app/compile/CompileState";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
export class SymbolNode {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(platform: Platform): string {
		return this.value;
	}
	resolve(state: CompileState): Type {
		return state.resolve(this.value).orElse(PrimitiveType.Auto);
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	generate(): string {
		return this.value;
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>();
	}
}
