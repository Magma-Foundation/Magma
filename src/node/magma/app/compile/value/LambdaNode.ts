import { Value } from "../../../../magma/app/compile/value/Value";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { List } from "../../../../magma/api/collect/list/List";
import { Platform } from "../../../../magma/app/io/Platform";
import { Joiner } from "../../../../magma/api/collect/Joiner";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
import { Type } from "../../../../magma/api/Type";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class LambdaNode implements Value {
	paramNames: List<Definition>;
	content: string;
	constructor (paramNames: List<Definition>, content: string) {
		this.paramNames = paramNames;
		this.content = content;
	}
	generate(platform: Platform): string {
		let joinedParamNames = this/*auto*/.paramNames.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames/*auto*/ + ")" + " => " + this/*auto*/.content;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
