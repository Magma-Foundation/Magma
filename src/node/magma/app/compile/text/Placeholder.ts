import { Main } from "../../../../magma/app/Main";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Type } from "../../../../magma/api/Type";
import { CompileState } from "../../../../magma/app/compile/CompileState";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class Placeholder {
	input: string;
	constructor (input: string) {
		this.input = input;
	}
	generate(): string {
		return Main.generatePlaceholder(this.input);
	}
	isFunctional(): boolean {
		return false;
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
	toValue(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return PrimitiveType.Unknown;
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
