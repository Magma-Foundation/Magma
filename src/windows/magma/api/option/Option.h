import { Tuple2 } from "magma/api/Tuple2";
export interface Option<T> {
	mut map<R>(mut mapper: (arg0 : T) => R): Option<R>;
	mut orElse(mut other: T): T;
	mut orElseGet(mut supplier: () => T): T;
	mut isPresent(): Bool;
	mut ifPresent(mut consumer: (arg0 : T) => void): void;
	mut or(mut other: () => Option<T>): Option<T>;
	mut flatMap<R>(mut mapper: (arg0 : T) => Option<R>): Option<R>;
	mut filter(mut predicate: (arg0 : T) => boolean): Option<T>;
	mut toTuple(mut other: T): Tuple2<Bool, T>;
	mut and<R>(mut other: () => Option<R>): Option<Tuple2<T, R>>;
}
