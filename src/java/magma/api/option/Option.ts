import from 'magma.app.Main'.ts;
import from 'java.util.function.Consumer'.ts;
import from 'java.util.function.Function'.ts;
import from 'java.util.function.Predicate'.ts;
import from 'java.util.function.Supplier'.ts;
interface Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R>;
	orElse(other: T): T;
	orElseGet(supplier: () => T): T;
	isPresent(): boolean;
	ifPresent(consumer: (arg0 : T) => void): void;
	or(other: () => Option<T>): Option<T>;
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R>;
	filter(predicate: (arg0 : T) => boolean): Option<T>;
	toTuple(other: T): Main.Tuple<Boolean, T>;
	and<R>(other: () => Option<R>): Option<Main.Tuple<T, R>>;
}
