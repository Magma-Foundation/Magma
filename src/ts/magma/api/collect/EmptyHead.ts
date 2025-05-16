import { None } from "../../../magma/api/option/None";
import { Option } from "../../../magma/api/option/Option";
import { Head } from "./Head";
export class EmptyHead<T> implements Head<T> {
	next(): Option<T> {
		return new None<T>();
	}
}
