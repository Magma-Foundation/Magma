import { Type } from "../../../../magma/api/Type";
import { Platform } from "../../../../magma/app/io/Platform";
import { Main } from "../../../../magma/app/Main";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
export class SymbolNode {
	value: string;
	type: Type;
	constructor (value: string, type: Type) {
		this.value = value;
		this.type = type;
	}
	generate(platform: Platform): string {
		return this/*auto*/.value + Main/*auto*/.generatePlaceholder(type/*Type*/.generate(/*auto*/));
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generate(): string {
		return this/*auto*/.value;
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
}
