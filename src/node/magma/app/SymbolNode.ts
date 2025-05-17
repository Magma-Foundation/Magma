import { Type } from "../../magma/api/Type";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Primitive } from "../../magma/app/Primitive";
import { Value } from "../../magma/app/Value";
import { Option } from "../../magma/api/option/Option";
import { Some } from "../../magma/api/option/Some";
import { None } from "../../magma/api/option/None";
class SymbolNode {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(): string {
		return this.value;
	}
	resolve(state: CompileState): Type {
		return state.resolve(this.value).orElse(Primitive.Unknown);
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
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
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
