import { Query } from "../../../../magma/api/collect/Query";
import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
import { Collector } from "../../../../magma/api/collect/Collector";
import { MapHead } from "../../../../magma/api/collect/head/MapHead";
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { FlatMapHead } from "../../../../magma/api/collect/head/FlatMapHead";
import { EmptyHead } from "../../../../magma/api/collect/head/EmptyHead";
import { ZipHead } from "../../../../magma/api/collect/head/ZipHead";
import { SingleHead } from "../../../../magma/api/collect/head/SingleHead";
export class HeadedQuery<T> implements Query<T> {
	head: Head<T>;
	constructor (head: Head<T>) {
		this.head = head;
	}
	next(): Option<T> {
		return this/*auto*/.head.next(/*auto*/);
	}
	collect<C>(collector: Collector<T, C>): C {
		return this/*auto*/.foldWithInitial(collector/*auto*/.createInitial(/*auto*/), (current: C, element: T) => collector/*auto*/.fold(current/*auto*/, element/*auto*/));
	}
	map<R>(mapper: (arg0 : T) => R): Query<R> {
		return new HeadedQuery<R>(new MapHead<T, R>(this/*auto*/.head, mapper/*auto*/));
	}
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
		let result: R = initial/*auto*/;
		while (true/*auto*/) {
			let finalResult: R = result/*auto*/;
			let maybeNext: Tuple2<boolean, R> = this/*auto*/.head.next(/*auto*/).map((inner: T) => folder/*auto*/(finalResult/*auto*/, inner/*auto*/)).toTuple(finalResult/*auto*/);
			if (maybeNext/*auto*/.left(/*auto*/)) {
				result/*auto*/ = maybeNext/*auto*/.right(/*auto*/);
			}
			else {
				return result/*auto*/;
			}
		}
	}
	foldWithMapper<R>(next: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R> {
		return this/*auto*/.head.next(/*auto*/).map(next/*auto*/).map((maybeNext: R) => this/*auto*/.foldWithInitial(maybeNext/*auto*/, folder/*auto*/));
	}
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return this/*auto*/.head.next(/*auto*/).map(mapper/*auto*/).map((initial: Query<R>) => new HeadedQuery<R>(new FlatMapHead<T, R>(this/*auto*/.head, initial/*auto*/, mapper/*auto*/))).orElseGet(() => new HeadedQuery<R>(new EmptyHead<R>(/*auto*/)));
	}
	allMatch(predicate: (arg0 : T) => boolean): boolean {
		return this/*auto*/.foldWithInitial(true/*auto*/, (maybeAllTrue: boolean, element: T) => maybeAllTrue/*auto*/ && predicate/*auto*/(element/*auto*/));
	}
	anyMatch(predicate: (arg0 : T) => boolean): boolean {
		return this/*auto*/.foldWithInitial(false/*auto*/, (aBoolean: boolean, t: T) => aBoolean/*auto*/ || predicate/*auto*/(t/*auto*/));
	}
	zip<R>(other: Query<R>): Query<Tuple2<T, R>> {
		return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this/*auto*/.head, other/*auto*/));
	}
	filter(predicate: (arg0 : T) => boolean): Query<T> {
		return this/*auto*/.flatMap((element: T) => {
			if (predicate/*auto*/(element/*auto*/)) {
				return new HeadedQuery<T>(new SingleHead<T>(element/*auto*/));
			}
			else {
				return new HeadedQuery<T>(new EmptyHead<T>(/*auto*/));
			}
		});
	}
}
