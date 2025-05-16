// [Actual, Namespace, Collector, EmptyHead]
import { Option } from "../../../magma/api/option/Option";
export interface Head<T> {
	next(): Option<T>;
}
