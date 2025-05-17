import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class EmptyHead<T> implements Head<T> {
	next(): Option<T> {
		return new None<T>(/*auto*/);
	}
}
