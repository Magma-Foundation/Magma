import { Option } from "../../../magma/api/option/Option";
import { Main } from "../../../magma/app/Main";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export interface Query<T> {
	collect<C>(collector: Main.Collector<T, C>): C;
	map<R>(mapper: (arg0 : T) => R): Query<R>;
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R;
	foldWithMapper<R>(mapper: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R>;
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R>;
	next(): Option<T>;
	allMatch(predicate: (arg0 : T) => boolean): boolean;
	filter(predicate: (arg0 : T) => boolean): Query<T>;
}
