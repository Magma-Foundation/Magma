import { Option } from "../../../../../magmac/api/Option";
export interface Locator {
	locate(input : String, infix : String) : Option<Integer>;
}
