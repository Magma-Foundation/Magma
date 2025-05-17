import { Value } from "../../../magma/app/Value";
import { Option } from "../../../magma/api/option/Option";
export interface Caller {
	generate(): string;
	findChild(): Option<Value>;
}
