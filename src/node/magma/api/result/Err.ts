// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None, None, None, Option, Option, Option, Some, Some, Some, Err]
import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { None } from "../../../magma/api/option/None";
export class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
	findError(): Option<X> {
		return new Some<X>(this/*auto*/.error);
	}
	findValue(): Option<T> {
		return new None<T>(/*auto*/);
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenErr/*(arg0 : X) => R*/(this/*auto*/.error);
	}
}
