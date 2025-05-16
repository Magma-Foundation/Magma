import { Option } from "../../../magma/api/option/Option";
import { Option } from "./Option";
export interface Head<T> {
	next(): Option<T>;
}
