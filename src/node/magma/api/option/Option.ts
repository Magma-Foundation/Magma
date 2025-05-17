import { Tuple2 } from "../../../magma/api/Tuple2";
export interface Option<T> {map<R>(mapper: (arg0 : T) => R): Option<R>;orElse(other: T): T;orElseGet(supplier: () => T): T;isPresent(): boolean;ifPresent(consumer: (arg0 : T) => void): void;or(other: () => Option<T>): Option<T>;flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R>;filter(predicate: (arg0 : T) => boolean): Option<T>;toTuple(other: T): Tuple2<boolean, T>;and<R>(other: () => Option<R>): Option<Tuple2<T, R>>;
}
