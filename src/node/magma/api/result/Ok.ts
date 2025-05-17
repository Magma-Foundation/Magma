// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None, None, None, Option, Option, Option, Some, Some, Some, Err, Err, Err, Ok]
import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
import { None } from "../../../magma/api/option/None";
import { Some } from "../../../magma/api/option/Some";
export class Ok<T, X> implements Result<T, X> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	findError(): Option<X> {
		return new None<X>(/*auto*/);
	}
	findValue(): Option<T> {
		return new Some<T>(this/*auto*/.value);
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenOk/*(arg0 : T) => R*/(this/*auto*/.value);
	}
}
