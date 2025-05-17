import { Definition } from "../../magma/app/Definition";
import { Option } from "../../magma/api/option/Option";
export interface Parameter {
	generate(): string;
	asDefinition(): Option<Definition>;
}
