import { Definition } from "../../magma/app/Definition";
import { Option } from "../../magma/api/option/Option";
interface Parameter {
	generate(): string;
	asDefinition(): Option<Definition>;
}
