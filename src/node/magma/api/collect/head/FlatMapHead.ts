// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead]
import { Head } from "../../../../magma/api/collect/head/Head";
import { Query } from "../../../../magma/api/collect/Query";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class FlatMapHead<T, R> implements Head<R> {
	mapper: (arg0 : T) => Query<R>;
	head: Head<T>;
	current: Query<R>;
	constructor (head: Head<T>, initial: Query<R>, mapper: (arg0 : T) => Query<R>) {
		this/*auto*/.head = head/*Head<T>*/;
		this/*auto*/.current = initial/*Query<R>*/;
		this/*auto*/.mapper = mapper/*(arg0 : T) => Query<R>*/;
	}
	next(): Option<R> {
		while (true/*auto*/) {
			let next = this/*auto*/.current.next(/*auto*/);
			if (next/*auto*/.isPresent(/*auto*/)) {
				return next/*auto*/;
			}
			let tuple = this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper).toTuple(this/*auto*/.current);
			if (tuple/*auto*/.left(/*auto*/)) {
				this/*auto*/.current = tuple/*auto*/.right(/*auto*/);
			}
			else {
				return new None<R>(/*auto*/);
			}
		}
	}
}
