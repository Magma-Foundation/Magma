import { Option } from "magma/api/option/Option";
export interface Head<T> {
	mut next(): Option<T>;
}
