/*[
	Actual, 
	Characters, 
	Collector, 
	Console, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	Head, 
	HeadedQuery, 
	IOError, 
	Joiner, 
	List, 
	ListCollector, 
	Lists, 
	MapHead, 
	Namespace, 
	None, 
	Ok, 
	Option, 
	Path, 
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	Some, 
	Strings, 
	ZipHead
]*/
import { Option } from "../../../magma/api/option/Option";
export interface Result<T, X> {
	findError(): Option<X>;
	findValue(): Option<T>;
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R;
	flatMapValue<R>(mapper: (arg0 : T) => Result<R, X>): Result<R, X>;
}
