// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result]
import { Option } from "../../../magma/api/option/Option";
export interface Result<T, X> {
	findError(): Option<X>;
	findValue(): Option<T>;
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R;
}
