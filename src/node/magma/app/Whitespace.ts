import { Parameter } from "../../magma/app/Parameter";
import { Definition } from "../../magma/app/Definition";
import { Option } from "../../magma/api/option/Option";
import { None } from "../../magma/api/option/None";
class Whitespace implements Parameter {
	generate(): string {
		return "";
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
}
