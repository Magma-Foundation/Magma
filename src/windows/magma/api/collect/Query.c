import { Collector } from "magma/api/collect/Collector";
import { Option } from "magma/api/option/Option";
import { Tuple2 } from "magma/api/Tuple2";
export interface Query<T> {
	mut collect<C>(mut collector: Collector<T, C>): C;
	mut map<R>(mut mapper: (arg0 : T) => R): Query<R>;
	mut foldWithInitial<R>(mut initial: R, mut folder: (arg0 : R, arg1 : T) => R): R;
	mut foldWithMapper<R>(mut mapper: (arg0 : T) => R, mut folder: (arg0 : R, arg1 : T) => R): Option<R>;
	mut flatMap<R>(mut mapper: (arg0 : T) => Query<R>): Query<R>;
	mut next(): Option<T>;
	mut allMatch(mut predicate: (arg0 : T) => boolean): Bool;
	mut filter(mut predicate: (arg0 : T) => boolean): Query<T>;
	mut anyMatch(mut predicate: (arg0 : T) => boolean): Bool;
	mut zip<R>(mut other: Query<R>): Query<Tuple2<T, R>>;
}
