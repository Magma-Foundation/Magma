import { Tuple2 } from "../../../magma/api/Tuple2";
import { Option } from "../../../magma/api/option/Option";
import { Main } from "../../../magma/app/Main";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedQuery<T> implements Query<T> {
	head: Head<T>;
	constructor (head: Head<T>) {
		this.head = head;
	}
	next(): Option<T> {
		return this.head.next();
	}
	collect<C>(collector: Main.Collector<T, C>): C {
		return this.foldWithInitial(collector.createInitial(), collector.fold);
	}
	map<R>(mapper: (arg0 : T) => R): Query<R> {
		return new HeadedQuery<R>(new Main.MapHead<T, R>(this.head, mapper));
	}
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
		let result: R = initial;
		while (true){
			let finalResult: R = result;
			let maybeNext: Tuple2<Boolean, R> = this.head.next().map((inner: T) => folder(finalResult, inner)).toTuple(finalResult);
			if (maybeNext.left()){
				result = maybeNext.right();
			}
			else {
				return result;
			}
		}
	}
	foldWithMapper<R>(next: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R> {
		return this.head.next().map(next).map((maybeNext: R) => {
			return this.foldWithInitial(maybeNext, folder);
		});
	}
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return this.head.next().map(mapper).map((initial: Query<R>) => new HeadedQuery<R>(new Main.FlatMapHead<T, R>(this.head, initial, mapper))).orElseGet(() => new HeadedQuery<R>(new Main.EmptyHead<R>()));
	}
	allMatch(predicate: (arg0 : T) => boolean): boolean {
		return this.foldWithInitial(true, (maybeAllTrue: Boolean, element: T) => maybeAllTrue && predicate(element));
	}
	filter(predicate: (arg0 : T) => boolean): Query<T> {
		return this.flatMap((element: T) => {
			if (predicate(element)){
				return new HeadedQuery<T>(new Main.SingleHead<T>(element));
			}
			else {
				return new HeadedQuery<T>(new Main.EmptyHead<T>());
			}
		});
	}
}
