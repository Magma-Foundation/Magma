import { Value } from "../../magma/app/Value";
import { Option } from "../../magma/api/option/Option";
import { Some } from "../../magma/api/option/Some";
import { None } from "../../magma/api/option/None";
import { Type } from "../../magma/api/Type";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Primitive } from "../../magma/app/Primitive";
class Operation implements Value {
	left: Value;
	targetInfix: string;
	right: Value;
	constructor (left: Value, targetInfix: string, right: Value) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
	generate(): string {
		return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
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
