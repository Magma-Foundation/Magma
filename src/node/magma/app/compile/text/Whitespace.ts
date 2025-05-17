import { Parameter } from "../../../../magma/app/compile/define/Parameter";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class Whitespace implements Parameter {
	generate(): string {
		return "";
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
}
