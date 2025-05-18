/*[
	Actual, 
	Characters, 
	Collector, 
	Console, 
	EmptyHead, 
	Files, 
	FlatMapHead, 
	Head, 
	Lists, 
	Namespace, 
	Strings
]*/
import { Option } from "../../../../magma/api/option/Option";
export interface Head<T> {
	next(): Option<T>;
}
