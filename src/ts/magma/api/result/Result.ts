// [Actual, Namespace, Collector, EmptyHead, Head, List, ListCollector, Lists, HeadedQuery, Query, RangeHead, SingleHead, Console, IOError, Path, None, Option, Some]
export interface Result<T, X> {
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R;
}
