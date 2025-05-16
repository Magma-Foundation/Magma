/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect
]*/
import { Option } from "../../../magma/api/option/Option";
export interface Head<T> {
	next(): Option<T>;
}
