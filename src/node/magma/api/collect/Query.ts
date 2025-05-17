/*[
	Actual, 
	Characters, 
	Collector, 
	Console, 
	EmptyHead, 
	Files, 
	FlatMapHead, 
	Head, 
	HeadedQuery, 
	Joiner, 
	List, 
	ListCollector, 
	Lists, 
	MapHead, 
	Namespace, 
	Queries, 
	Query, 
	RangeHead, 
	SingleHead, 
	Strings, 
	ZipHead
]*/
import { Collector } from "../../../magma/api/collect/Collector";
import { Option } from "../../../magma/api/option/Option";
import { Tuple2 } from "../../../magma/api/Tuple2";
export interface Query<T> {
	collect<C>(collector: Collector<T, C>): C;
	map<R>(mapper: (arg0 : T) => R): Query<R>;
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R;
	foldWithMapper<R>(mapper: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R>;
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R>;
	next(): Option<T>;
	allMatch(predicate: (arg0 : T) => boolean): boolean;
	filter(predicate: (arg0 : T) => boolean): Query<T>;
	anyMatch(predicate: (arg0 : T) => boolean): boolean;
	zip<R>(other: Query<R>): Query<Tuple2<T, R>>;
}
